import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Random;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
/**
 * Date:22/12/2022
 * @author jinniu
 * @version 1.0
 */
public class Paint
{
	//�ַ�������������5�ֻ滭ѡ�񣬷ֱ��Ǿ��Σ�Բ�Σ���׼Բ�������Σ����ֻ�
	private final String RECT_SHAPE = "rect";
	private final String OVAL_SHAPE = "oval";
	private final String YUAN_SHAPE = "yuan";
	private final String ZFRT_SHAPE = "zfrt";
	private final String SIMP_DRAW  = "simp";
	//�����滭ѡ���Ӧ��5����ť
	private Button rect = new Button("���ƾ���");
	private Button oval = new Button("����Բ��");
	private Button zfrt = new Button("����������");
	private Button yuan = new Button("���Ʊ�׼Բ");
	private Button simp = new Button("���ֻ�");
	//���水ť
	private Button save = new Button("���滭��");
	//Ĭ��Ϊ���ֻ�
	private String shape = SIMP_DRAW;
	// ��ͼ���Ŀ��
	private final int AREA_WIDTH = 1200;
	// ��ͼ���ĸ߶�
	private final int AREA_HEIGHT = 600;
	// �����preX��preY��������һ������϶��¼����������
	private int preX = -1;
	private int preY = -1;
	// ����һ���Ҽ��˵��������û�����ɫ
	PopupMenu pop = new PopupMenu();
	MenuItem redItem = new MenuItem("��ɫ");
	MenuItem greenItem = new MenuItem("��ɫ");
	MenuItem blueItem = new MenuItem("��ɫ");
	MenuItem purpleItem = new MenuItem("��ɫ");
	// ����һ��BufferedImage����
	BufferedImage image = new BufferedImage(AREA_WIDTH, AREA_HEIGHT, BufferedImage.TYPE_INT_RGB);
	// ��ȡimage�����Graphics
	Graphics g = image.getGraphics();
	private Frame f = new Frame("lanpaint");
	private DrawCanvas drawArea = new DrawCanvas();
	// ���ڱ��滭����ɫ
	private Color foreColor = new Color(255, 0, 0);
	//��������϶��¼��������յ������Թ���ͼ
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	//���滭��
	public void savepic()
	{
		// ��imageͼ���ļ�����������ļ��С�
		try
		{
			ImageIO.write(image, "jpeg", new File("lanpaint.jpg"));
		}
		catch (IOException e) {
            e.printStackTrace();
		}
	}
	public void init()
	{
		var p = new Panel();
		rect.addActionListener(e ->
		{
			// ����shape����ΪRECT_SHAPE
			shape = RECT_SHAPE;
		});
		zfrt.addActionListener(e ->
		{
			// ����shape����ΪRECT_SHAPE
			shape = ZFRT_SHAPE;
		});
		oval.addActionListener(e ->
		{
			// ����shape����ΪOVAL_SHAPE
			shape = OVAL_SHAPE;
		});
		yuan.addActionListener(e ->
		{
			// ����shape����ΪRECT_SHAPE
			shape = YUAN_SHAPE;
		});
		simp.addActionListener(e ->
		{
			// ����shape����ΪOVAL_SHAPE
			shape = SIMP_DRAW;
		});
		save.addActionListener(e ->
		{
			savepic();	
		});
		p.add(rect);
		p.add(zfrt);
		p.add(oval);
		p.add(yuan);
		p.add(simp);
		p.add(save);
		// �����Ҽ��˵����¼���������
		ActionListener menuListener = e ->
		{
			if (e.getActionCommand().equals("��ɫ"))
			{
				foreColor = new Color(0, 255, 0);
			}
			if (e.getActionCommand().equals("��ɫ"))
			{
				foreColor = new Color(255, 0, 0);
			}
			if (e.getActionCommand().equals("��ɫ"))
			{
				foreColor = new Color(0, 0, 255);
			}
			if (e.getActionCommand().equals("��ɫ"))
			{
				foreColor = new Color(128, 0, 128);
			}
			
		};
		// Ϊ�����˵�����¼�������
		redItem.addActionListener(menuListener);
		greenItem.addActionListener(menuListener);
		blueItem.addActionListener(menuListener);
		purpleItem.addActionListener(menuListener);
		// ���˵�����ϳ��Ҽ��˵�
		pop.add(redItem);
		pop.add(greenItem);
		pop.add(blueItem);
		pop.add(purpleItem);
		// ���Ҽ��˵���ӵ�drawArea������
		drawArea.add(pop);
		// ��image����ı���ɫ���ɰ�ɫ
		g.fillRect(0, 0, AREA_WIDTH, AREA_HEIGHT);
		drawArea.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
		// ��������ƶ�����
		drawArea.addMouseMotionListener(new MouseMotionAdapter()
		{
			// ʵ�ְ����������϶����¼�������
			public void mouseDragged(MouseEvent e)
			{
				// ����Ǽ��ֻ�ģʽ���Ǿ͸��ݻ������ߣ�
				if (shape.equals(SIMP_DRAW))
				{
					if (preX > 0 && preY > 0)
					{
						// ���õ�ǰ��ɫ
						g.setColor(foreColor);
						// ���ƴ���һ������϶��¼��㵽��������϶��¼�����߶�
						g.drawLine(preX, preY, e.getX(), e.getY());
					}
					// ����ǰ����¼����X��Y���걣������
					preX = e.getX();
					preY = e.getY();
					// �ػ�drawArea����
					drawArea.repaint();
				}
				//����ͼ�¼�»�������β����
				else
				{
					if (preX > 0 && preY > 0)
					{
						x2 = e.getX();
						y2 = e.getY();
					}
					// ����ǰ����¼����X��Y���걣������
					else
					{
					x1 = e.getX();
					y1 = e.getY();
					preX = e.getX();
					preY = e.getY();
					}
				}
			}			
		});
		// ��������¼�
		drawArea.addMouseListener(new MouseAdapter()
		{
			// ʵ������ɿ����¼�������
			public void mouseReleased(MouseEvent e)
			{
				// �����Ҽ��˵�
				if (e.isPopupTrigger())
				{
					pop.show(drawArea, e.getX(), e.getY());
				}
				//���ݻ�ͼģʽ����β���꿪ʼ��ͼ
				if (shape.equals(OVAL_SHAPE))
				{
					g.setColor(foreColor);
					g.fillOval(x1, y1, x2 - x1, y2 - y1);
				}
				if (shape.equals(YUAN_SHAPE))
				{
					g.setColor(foreColor);
					g.fillOval(x1, y1, x2 - x1, x2 - x1);
				}
				if (shape.equals(RECT_SHAPE))
				{
					g.setColor(foreColor);
					g.drawRect(x1, y1, x2 - x1, y2 - y1);
				}
				if (shape.equals(ZFRT_SHAPE))
				{
					g.setColor(foreColor);
					g.drawRect(x1, y1, x2 - x1, x2 - x1);
				}
				preX = -1;
				preY = -1;
				drawArea.repaint();
				
			}
		});
		class MyListener extends WindowAdapter
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		}
		f.addWindowListener(new MyListener());
		f.add(drawArea);
		f.add(p, BorderLayout.SOUTH);
		f.pack();
		f.setVisible(true);
	}
	public static void main(String[] args)
	{
		new Paint().init();
	}
	class DrawCanvas extends Canvas
	{
		// ��дCanvas��paint������ʵ�ֻ滭
		public void paint(Graphics g)
		{
				g.drawImage(image, 0, 0, null);
		}
	}
}
