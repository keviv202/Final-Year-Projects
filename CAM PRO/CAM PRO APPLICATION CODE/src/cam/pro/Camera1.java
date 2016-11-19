package cam.pro;

import java.net.*;
import com.sun.image.codec.jpeg.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class Camera1 extends JPanel implements Runnable
{
    public boolean useMJPGStream = true;
    public String mjpgURL=null;
    DataInputStream dis;
    private BufferedImage image=null;
    int counter=0;
 
    public Dimension imageSize = null;
    public boolean connected = false;
    private boolean initCompleted = false;
    HttpURLConnection huc=null;
    JMenuBar jm;
    JMenu j;
    Component parent;
 
    public Camera1(Component parent_ , String st) 
    {
        parent = parent_;
        mjpgURL=st;
    }
 
    public static Image getScaledInstanceAWT(BufferedImage source, double factor) 
    {
        int w = (int) (source.getWidth() * factor);
        int h = (int) (source.getHeight() * factor);
        return source.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    }
 
    public static BufferedImage toBufferedImage(Image image) 
    {
        new ImageIcon(image); //load image
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage bimage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
 
    public void connect()
    {
        try
        {
            URL u = new URL(mjpgURL);
            huc = (HttpURLConnection) u.openConnection();
            System.out.println(huc.getInputStream());
            InputStream is = huc.getInputStream();
            connected = true;
            BufferedInputStream bis = new BufferedInputStream(is);
            dis= new DataInputStream(bis);
            if (!initCompleted) initDisplay();
        }
        catch(IOException e)
        {  
            try
            {
                huc.disconnect();
                Thread.sleep(1);
            }
            catch(InterruptedException ie)
            {
            }
        }
        catch(Exception e){;}
    }
 
    public void initDisplay()
    { 
        if (useMJPGStream)
            readMJPGStream();
        else
        {
            readJPG();
            disconnect();
        }
        imageSize = new Dimension(image.getWidth(this), image.getHeight(this));
        setPreferredSize(imageSize);
        parent.setSize(imageSize);
        parent.validate();
        initCompleted = true;
    }
 
    public void disconnect(){
        try
        {
            if(connected)
            {
                dis.close();
                connected = false;
             }
         }
        catch(Exception e){}
    }
    
    public void paint(Graphics g) 
    { 
        if (image != null)
        g.drawImage(image, 0, 0, this);
    }
 
    public void readStream()
    { //the basic method to continuously read the stream
        try
        {
            if (useMJPGStream)
            {
                while(true)
                {
                    readMJPGStream();
                    parent.repaint();
                }
            }
            else
            {
                while(true)
                {
                    connect();
                    readJPG();
                    parent.repaint();
                    disconnect(); 
                }
            }
        }
        catch(Exception e){}
    }
 
    public void readMJPGStream()
    {
        readLine(4,dis); //discard the first 3 lines
        readJPG();
        readLine(1,dis); //discard the last two lines
    }
 
    public void readJPG()
    { //read the embedded jpeg image
        try
        {
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(dis);
            image = decoder.decodeAsBufferedImage();
            char[] c=mjpgURL.toCharArray();
            String u="";
            for(int i=7;i<=c.length;i++)
            {
                if(c[i]!='/')
                u=u+c[i]; 
                else
                break;
            }
            String st="c:\\stream\\"+u+"\\";
            File dir = new File(st);
            dir.mkdir();
            String s="a"+counter+".jpg";
            counter++;
            String filename=st+s;
            System.out.println(filename);
            BufferedImage smaller = toBufferedImage(getScaledInstanceAWT(image, 1.0/3.0));//scalling image size
            try 
            {
                OutputStream out = new FileOutputStream(filename);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(smaller);
                out.close();
             }
            catch (Exception e) 
            {
                System.out.println(e);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();disconnect();
        }
    }
 
    public void readLine(int n, DataInputStream dis)
    { //used to strip out the header lines
        for (int i=0; i<n;i++)
        readLine(dis);
    }
 
    public void readLine(DataInputStream dis)
    {
        try
        {
            boolean end = false;
            String lineEnd = "\n"; //assumes that the end of the line is marked with this
            byte[] lineEndBytes = lineEnd.getBytes();
            byte[] byteBuf = new byte[lineEndBytes.length];
            while(!end)
            {
                dis.read(byteBuf,0,lineEndBytes.length);
                String t = new String(byteBuf);
                System.out.print(t); //uncomment if you want to see what the lines actually look like
                if(t.equals(lineEnd)) 
                    end=true;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void run() 
    {
        System.out.println("in Run...................");
        connect();
        readStream();
    }
 
    public static void main(String[] args) {
    }
     public static void startCamera(String st)
     {
        JFrame jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Camera1 axPanel = new Camera1(jframe,st);
        new Thread(axPanel).start();
        jframe.setTitle("IP Camera Stream");
        jframe.setLocation(520, 250);
        jframe.getContentPane().add(axPanel);
        jframe.pack();
        jframe.show();
    }
}
