package com.czh.basicframe.ui;

import android.Manifest;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.utils.PermissionUtils;
import com.czh.basicframe.utils.Tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * create by Chen
 * create date : 2019/11/19
 * desc :
 */
public class VoiceFragment extends BaseFragment {
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.file_Path_tv)
    TextView filePathTv;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.ip_tv)
    TextView ipTv;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.state_tv)
    TextView stateTv;

    private MediaRecorder mediaRecorder;
    private boolean isStarting;

    @Override
    protected int setLayout() {
        return R.layout.fragment_voice;
    }

    @Override
    protected void initValue(Bundle bundle) {

    }

    @Override
    protected void main() {
        ipTv.setText("当前IP : " + Tools.getInstance().getIpAddress());
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1://开始录音
                PermissionUtils.getInstance().checkPermissions(mActivity, new String[]{Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111, (isSuccess, requestCode) -> {
                    if (isSuccess) {
                        if (!isStarting) {
                            startRecord();
                            btn1.setText("停止录音");
                            isStarting = true;
                        } else {
                            stopRecord();
                            btn1.setText("开始录音");
                            isStarting = false;
                        }
                    }
                });
                break;
            case R.id.btn2://播放录音
                startPlay();
                break;
            case R.id.btn3://启动接收服务
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startReveiceService();
                    }
                }).start();
                break;
            case R.id.btn4://发送数据
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendVoice();
                    }
                }).start();
                break;
        }
    }

    /**
     * 发送语音数据
     */
    private void sendVoice() {
        try {
            InetAddress address = InetAddress.getByName("192.168.2.4");
//            InetAddress address = InetAddress.getByName("localhost");
            byte[] bytes = getBytes(voiceFile.getAbsolutePath());
            //创建数据报
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 9999);
            //创建socket对象
            DatagramSocket socket = new DatagramSocket();
            //发送数据
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024*10);
            byte[] b = new byte[1024*10];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * 开启接收的服务端
     */
    private File receiveFile;

    private void startReveiceService() {
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream os = null;
        try {
            //创建服务端连接对象 -- 指定端口号：8633
            DatagramSocket socket = new DatagramSocket(9999);
            //创建数据报
            byte[] data = new byte[1024 * 10];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            //接收数据
            getActivity().runOnUiThread(() -> stateTv.setText("服务端已创建，等待接收"));
            socket.receive(packet);
            //接收到数据，读取数据
            File path = new File(getContext().getExternalCacheDir(), "receive");
            if (!path.exists()) {
                path.mkdirs();
            }
            getFile(data,path.getAbsolutePath(),"r" + System.currentTimeMillis() + ".amr");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (receiveFile != null) {
            getActivity().runOnUiThread(() -> stateTv.setText("已接收：" + receiveFile.getAbsolutePath()));
        }

    }

    /**
     * 根据byte数组，生成文件
     */
    public void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        receiveFile = file;
    }

    private MediaPlayer mediaPlayer;

    /**
     * 开始播放
     */
    private void startPlay() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(receiveFile.getAbsolutePath());
                mediaPlayer.setLooping(false);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mediaPlayer.pause();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            startPlay();
        }
    }

    /**
     * 停止录音
     */
    private void stopRecord() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if (voiceFile != null) {
            filePathTv.setText(voiceFile.getAbsolutePath());
        }
    }

    /**
     * 开始录音
     */
    private File voiceFile;

    private void startRecord() {
        if (mediaRecorder == null) {
            File path = new File(getContext().getExternalCacheDir(), "voice");
            if (!path.exists()) {
                path.mkdirs();
            }
            voiceFile = new File(path, System.currentTimeMillis() + ".amr");
            if (!voiceFile.exists()) {
                try {
                    voiceFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//音频来源 -- 麦克风
            mediaRecorder.setOutputFormat(MediaRecorder.AudioEncoder.AMR_WB);//输出格式 --
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);// 编码格式
            mediaRecorder.setOutputFile(voiceFile.getAbsolutePath());
            try {
                mediaRecorder.prepare();//准备就绪
                mediaRecorder.start();//开始录制
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
