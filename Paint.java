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
	//字符常量用来保存5种绘画选择，分别是矩形，圆形，标准圆，正方形，简单手绘
	private final String RECT_SHAPE = "rect";
	private final String OVAL_SHAPE = "oval";
	private final String YUAN_SHAPE = "yuan";
	private final String ZFRT_SHAPE = "zfrt";
	private final String SIMP_DRAW  = "simp";
	//上述绘画选择对应的5个按钮
	private Button rect = new Button("绘制矩形");
	private Button oval = new Button("绘制圆形");
	private Button zfrt = new Button("绘制正方形");
	private Button yuan = new Button("绘制标准圆");
	private Button simp = new Button("简单手绘");
	//保存按钮
	private Button save = new Button("保存画作");
	//默认为简单手绘
	private String shape = SIMP_DRAW;
	// 画图区的宽度
	private final int AREA_WIDTH = 1200;
	// 画图区的高度
	private final int AREA_HEIGHT = 600;
	// 下面的preX、preY保存了上一次鼠标拖动事件的鼠标坐标
	private int preX = -1;
	private int preY = -1;
	// 定义一个右键菜单用于设置画笔颜色
	PopupMenu pop = new PopupMenu();
	MenuItem redItem = new MenuItem("红色");
	MenuItem greenItem = new MenuItem("绿色");
	MenuItem blueItem = new MenuItem("蓝色");
	MenuItem purpleItem = new MenuItem("紫色");
	// 定义一个BufferedImage对象
	BufferedImage image = new BufferedImage(AREA_WIDTH, AREA_HEIGHT, BufferedImage.TYPE_INT_RGB);
	// 获取image对象的Graphics
	Graphics g = image.getGraphics();
	private Frame f = new Frame("lanpaint");
	private DrawCanvas drawArea = new DrawCanvas();
	// 用于保存画笔颜色
	private Color foreColor = new Color(255, 0, 0);
	//保存鼠标拖动事件的起点和终点坐标以供绘图
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	//保存画作
	public void savepic()
	{
		// 将image图像文件输出到磁盘文件中。
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
			// 设置shape变量为RECT_SHAPE
			shape = RECT_SHAPE;
		});
		zfrt.addActionListener(e ->
		{
			// 设置shape变量为RECT_SHAPE
			shape = ZFRT_SHAPE;
		});
		oval.addActionListener(e ->
		{
			// 设置shape变量为OVAL_SHAPE
			shape = OVAL_SHAPE;
		});
		yuan.addActionListener(e ->
		{
			// 设置shape变量为RECT_SHAPE
			shape = YUAN_SHAPE;
		});
		simp.addActionListener(e ->
		{
			// 设置shape变量为OVAL_SHAPE
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
		// 定义右键菜单的事件监听器。
		ActionListener menuListener = e ->
		{
			if (e.getActionCommand().equals("绿色"))
			{
				foreColor = new Color(0, 255, 0);
			}
			if (e.getActionCommand().equals("红色"))
			{
				foreColor = new Color(255, 0, 0);
			}
			if (e.getActionCommand().equals("蓝色"))
			{
				foreColor = new Color(0, 0, 255);
			}
			if (e.getActionCommand().equals("紫色"))
			{
				foreColor = new Color(128, 0, 128);
			}
			
		};
		// 为三个菜单添加事件监听器
		redItem.addActionListener(menuListener);
		greenItem.addActionListener(menuListener);
		blueItem.addActionListener(menuListener);
		purpleItem.addActionListener(menuListener);
		// 将菜单项组合成右键菜单
		pop.add(redItem);
		pop.add(greenItem);
		pop.add(blueItem);
		pop.add(purpleItem);
		// 将右键菜单添加到drawArea对象中
		drawArea.add(pop);
		// 将image对象的背景色填充成白色
		g.fillRect(0, 0, AREA_WIDTH, AREA_HEIGHT);
		drawArea.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
		// 监听鼠标移动动作
		drawArea.addMouseMotionListener(new MouseMotionAdapter()
		{
			// 实现按下鼠标键并拖动的事件处理器
			public void mouseDragged(MouseEvent e)
			{
				// 如果是简单手绘模式，那就根据滑动划线，
				if (shape.equals(SIMP_DRAW))
				{
					if (preX > 0 && preY > 0)
					{
						// 设置当前颜色
						g.setColor(foreColor);
						// 绘制从上一次鼠标拖动事件点到本次鼠标拖动事件点的线段
						g.drawLine(preX, preY, e.getX(), e.getY());
					}
					// 将当前鼠标事件点的X、Y坐标保存起来
					preX = e.getX();
					preY = e.getY();
					// 重绘drawArea对象
					drawArea.repaint();
				}
				//否则就记录下滑动的首尾坐标
				else
				{
					if (preX > 0 && preY > 0)
					{
						x2 = e.getX();
						y2 = e.getY();
					}
					// 将当前鼠标事件点的X、Y坐标保存起来
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
		// 监听鼠标事件
		drawArea.addMouseListener(new MouseAdapter()
		{
			// 实现鼠标松开的事件处理器
			public void mouseReleased(MouseEvent e)
			{
				// 弹出右键菜单
				if (e.isPopupTrigger())
				{
					pop.show(drawArea, e.getX(), e.getY());
				}
				//根据绘图模式及首尾坐标开始绘图
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
		// 重写Canvas的paint方法，实现绘画
		public void paint(Graphics g)
		{
				g.drawImage(image, 0, 0, null);
		}
	}
}
