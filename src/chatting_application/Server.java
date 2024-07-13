package chatting_application;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;


public class Server implements ActionListener {
    JTextField text;
    JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static JFrame f=new JFrame();
    static DataOutputStream dout;
    public Server(){
        f.setLayout(null);

        JPanel p1=new JPanel();
        p1.setBackground(new Color(0,0,0));
        p1.setBounds(0,0,400,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5=i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profilepic=new JLabel(i6);
        profilepic.setBounds(40,10,50,50);
        p1.add(profilepic);

        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel video=new JLabel(i9);
        video.setBounds(270,20,25,25);
        p1.add(video);

        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(30,25,Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel phone=new JLabel(i12);
        phone.setBounds(310,20,30,25);
        p1.add(phone);

        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(5,20,Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel morevert=new JLabel(i15);
        morevert.setBounds(360,20,5,20);
        p1.add(morevert);

        JLabel name=new JLabel("Heera SDB");
        name.setBounds(100,10,130,28);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(name);

        JLabel status=new JLabel("Active Now");
        status.setBounds(100,25,130,28);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,10));
        p1.add(status);

        a1=new JPanel();
        a1.setBounds(3,75,395,470);
        f.add(a1);

        text=new JTextField();
        text.setBounds(5,555,312,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
        f.add(text);

        JButton send=new JButton("Send");
        send.setBounds(320,555,72,38);
        send.setBackground(new Color(0,0,0));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        f.add(send);

        f.setSize(400,600);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);


        f.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
        String out=text.getText();

        JPanel p2=formatLabel(out);

        a1.setLayout(new BorderLayout());

        JPanel right=new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(10));

        a1.add(vertical,BorderLayout.PAGE_START);

        dout.writeUTF(out);

        text.setText("");

        f.repaint();
        f.invalidate();
        f.validate();

        }catch (Exception e1){
            e1.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output=new JLabel(out);
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(192,192,192));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10,10,10,30));

        panel.add(output);

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");


        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);


        return panel;
    }
    public static void main(String[] args) {
        Server s=new Server();

        try{
            ServerSocket skt=new ServerSocket(6001);
            while (true){
                Socket sk = skt.accept();
                DataInputStream din=new DataInputStream(sk.getInputStream());
                dout=new DataOutputStream(sk.getOutputStream());

                while (true){
                    String msg=din.readUTF();
                    JPanel panel=formatLabel(msg);

                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
