package Gobang;

import java.awt.BorderLayout;
//import java.awt.GridLayout;










import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
//import javax.swing.JPanel;

public class GobangJframe extends JFrame{
	private static final long serialVersionUID = 6552241505775581565L;
	
	private Gobangpanel Gpannel =new Gobangpanel();
	
	JButton huiqi= new JButton("悔棋");
	JButton newgame= new JButton("新游戏");
	JCheckBox luoziorder=new JCheckBox("显示落子顺序");
	JRadioButton renren =new JRadioButton("人人");
	JRadioButton renji =new JRadioButton("人机");
	JRadioButton guzhi =new JRadioButton("估值函数");
	JRadioButton guzhitree =new JRadioButton("估值函数+搜索树");
	JComboBox<Integer> canjunum =new JComboBox<Integer>(new Integer[]{0,1,2,3,4,5,6,7,8});
	
	public void start() {
		setTitle("五子棋");
		add(Gpannel,BorderLayout.WEST);
		JPanel rightpanel= new JPanel();
		rightpanel.setLayout(new BoxLayout(rightpanel, BoxLayout.Y_AXIS));
		
		//文本域
		JPanel panel1=new JPanel(new BorderLayout());
		panel1.setBorder(new TitledBorder("在棋盘上点击鼠标右键，查看各点估值"));
		JTextArea tarea =new JTextArea();
		tarea.setEditable(false);
		panel1.add(tarea);
		rightpanel.add(panel1);
		
		//模式
		JPanel panel2=new JPanel();
		panel2.setBorder(new TitledBorder("模式"));	
		renren.setSelected(true);
		renren.addMouseListener(mouselistener);
		renji.addMouseListener(mouselistener);
		ButtonGroup group1=new ButtonGroup();
		group1.add(renren);
		group1.add(renji);
		panel2.add(renren);
		panel2.add(renji);
		rightpanel.add(panel2);
		
		//智能
		JPanel panel3=new JPanel();
		panel3.setBorder(new TitledBorder("智能"));
		ButtonGroup group2=new ButtonGroup();
		guzhi.addMouseListener(mouselistener);
		guzhitree.addMouseListener(mouselistener);
		guzhi.setSelected(true);
		group2.add(guzhi);
		group2.add(guzhitree);
		panel3.add(guzhi);
		panel3.add(guzhitree);
		rightpanel.add(panel3);
		
		//搜索树
		JPanel panel4=new JPanel();
		panel4.setBorder(new TitledBorder("搜索树"));
		panel4.add(new JLabel("搜索深度"));
		panel4.add(new JComboBox<Integer>(new Integer[]{1,3,5}));
		panel4.add(new JLabel("每层节点"));
		panel4.add(new JComboBox<Integer>(new Integer[]{3,5,10}));
		rightpanel.add(panel4);
		
		//其他
		JPanel panel5=new JPanel();
		luoziorder.addMouseListener(mouselistener);
		huiqi.addMouseListener(mouselistener);
		panel5.add(luoziorder);
		panel5.add(huiqi);
		rightpanel.add(panel5);
		
		//新游戏
		JPanel panel6=new JPanel();
		newgame.addMouseListener(mouselistener);
		panel6.add(newgame);
		rightpanel.add(panel6);
		
		//残局
		JPanel panel7=new JPanel();
		panel7.add(new JLabel("残局"));
		panel7.add(canjunum);
		rightpanel.add(panel7);
		
		
		
		add(rightpanel);
		setResizable(false);
		setSize(Gobangutil.FRAME_WIDTH, Gobangutil.FRAME_HEIGH);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
MouseAdapter mouselistener=new MouseAdapter() {
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		System.out.println("鼠标点击");
		Object obj=e.getSource();
		System.out.println("switch");
		//模式
		if(obj==renji)
		{
			Gpannel.modechange(true);
			Gpannel.setCount(2);
			System.out.println("renji");
		}
		else if(obj==renren)
		{
			Gpannel.modechange(false);
			System.out.println("renren");
		}
		//智能
		else if(obj==guzhitree){
			if(guzhitree.isSelected())
			Gpannel.zhinengchange(true);
			System.out.println("guzhitree");
		}
		//悔棋
		else if(obj==huiqi){
			Gpannel.undo();
			System.out.println("undo");
		}
		else if(newgame==obj){
			System.out.println("newgame");
			JOptionPane.showMessageDialog(GobangJframe.this, "Game start!");
			Gpannel.setCanjumode(canjunum.getSelectedIndex());
			Gpannel.newset();
			Gpannel.setGamestart(true);
		}
		else if(luoziorder==obj){
			System.out.println("ordershowchange");
			Gpannel.changeshoworder();
		}
		else if(canjunum==obj){
			System.out.println("chanjumode");
			Gpannel.setCanjumode(canjunum.getSelectedIndex());
		}
	}
};
}
