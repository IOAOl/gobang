package Gobang;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PageAttributes.ColorType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Gobangpanel extends JPanel {

	
	//data
	private Data canjudata =new Data();
	
	private final int EMPTY = 0;
	// //1������ӣ�2�������
	private final int BLACK = 1;
	private final int WHITE = 2;
	private int currentplayer = 1;
	private boolean isshoworder = false;
	private int count = 1;

	// ģʽ
	private final boolean renrenMode = false;
	private final boolean renjimode = true;
	private boolean mode = false;
	private int canjumode =0;  //�о�ģʽ

	// ����
	private final boolean guzhimode = false;
	
	//�������
	private int depth=1;
	
	//
	
	//���̵�״̬
	private boolean WIN=false;
	
	public void setCount(int count) {
		this.count = count;
	}

	private final boolean guzhitreemode = true;
	private boolean zhinengmode = false;
	// Ԥѡ��
	private int curx = Gobangutil.LINE_COUNT / 2;
	private int cury = Gobangutil.LINE_COUNT / 2;

	// ������¼�
	private int x = 0;
	private int y = 0;

	// ��Ϸ�Ƿ�ʼ
	private boolean isGamestart = false;

	private chess[][] chess1 = new chess[Gobangutil.LINE_COUNT][Gobangutil.LINE_COUNT];

	public Gobangpanel() {
		fillchess();
		this.setBackground(Color.ORANGE);
		setPreferredSize(new Dimension(Gobangutil.PANEL_WIDTH, Gobangutil.PANEl_HEIGH));
		// Ԥѡ���¼�
		this.addMouseMotionListener(mousemoveevent);
		// ����
		this.addMouseListener(mouselistener);
		;
	}

	private void fillchess() {
		for (int i = 0; i < Gobangutil.LINE_COUNT; i++)
			for (int j = 0; j < Gobangutil.LINE_COUNT; j++) {
				chess ch = new chess(i, j, 0, EMPTY);
				chess1[i][j] = ch;
			}
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		super.paintComponent(g2d);
		// ����
		drawline(g2d);
		// ����Ԫ����
		drawstar(g2d);
		// ����Ԥѡ��
		duawtips(g2d, curx, cury);
		// ������ߺ��±ߵ����ֻ���ĸ
		drawnum(g2d);
		// ������������
		drawchess(g2d);
		// �������
		draworder(g2d);

	}

	private void draworder(Graphics2D g2d) {
		if (isshoworder) {
			for (int i = 0; i < Gobangutil.LINE_COUNT; i++) {
				for (int j = 0; j < Gobangutil.LINE_COUNT; j++) {
					if (!chess1[i][j].isempty()) {
						if (chess1[i][j].getPlayer() == BLACK) {
							g2d.setColor(Color.WHITE);
						} else if (chess1[i][j].getPlayer() == WHITE) {
							g2d.setColor(Color.BLACK);
						}
						FontMetrics fm = g2d.getFontMetrics();
						String str = chess1[i][j].getOrdernum() + "";
						int heigh = fm.getAscent();
						int width = fm.stringWidth(str);
						int x = chess1[i][j].getX() * Gobangutil.INTERVAL + Gobangutil.OFFSET - width / 2;
						int y = chess1[i][j].getY() * Gobangutil.INTERVAL + Gobangutil.OFFSET + heigh / 2;
						g2d.drawString(str, x, y);
					}
				}
			}
		}
	}

	private void drawchess(Graphics2D g2d) {
		for (int i = 0; i < Gobangutil.LINE_COUNT; i++) {
			for (int j = 0; j < Gobangutil.LINE_COUNT; j++) {
				if (!chess1[i][j].isempty()) {
					if (chess1[i][j].getPlayer() == BLACK) {
						g2d.setColor(Color.BLACK);
					} else if (chess1[i][j].getPlayer() == WHITE) {
						g2d.setColor(Color.WHITE);
					}
					int width = Gobangutil.INTERVAL / 4 * 3;
					int x = chess1[i][j].getX() * Gobangutil.INTERVAL + Gobangutil.OFFSET - width / 2;
					int y = chess1[i][j].getY() * Gobangutil.INTERVAL + Gobangutil.OFFSET - width / 2;
					g2d.fillOval(x, y, width, width);
				}
			}
		}
		if (!isshoworder) {
			if (getcurchess().getPlayer() != EMPTY) {
				g2d.setColor(Color.RED);
				chess cur = getcurchess();
				int width1 = Gobangutil.INTERVAL / 5;
				int x1 = cur.getX() * Gobangutil.INTERVAL + Gobangutil.OFFSET - width1 / 2;
				int y1 = cur.getY() * Gobangutil.INTERVAL + Gobangutil.OFFSET - width1 / 2;
				g2d.fillRect(x1, y1, width1, width1);
				g2d.setColor(Color.BLACK);
			}
		}
	}

	private chess getcurchess() {
		for (int i = 0; i < Gobangutil.LINE_COUNT; i++) {
			for (int j = 0; j < Gobangutil.LINE_COUNT; j++) {
				if (chess1[i][j] != null && chess1[i][j].getOrdernum() == count - 1) {
					return chess1[i][j];

				}
			}
			/*
			 * for(int i=0;i<Gobangutil.LINE_COUNT;i++){ for(int
			 * j=0;j<Gobangutil.LINE_COUNT;j++){
			 * if(chess1[i][j]!=null&&chess1[i][j].getOrdernum()==count-1) {
			 * System.out.println(); return chess1[i][j];
			 * 
			 * } }
			 */
		}
		return null;
	}

	private void drawnum(Graphics2D g2d) {
		for (int i = 0; i < Gobangutil.LINE_COUNT; i++) {
			FontMetrics fm = g2d.getFontMetrics();
			int h = fm.getAscent();
			int w = fm.stringWidth("A");
			int x = 10;
			int y = Gobangutil.INTERVAL * i + Gobangutil.OFFSET + h / 2;
			g2d.drawString((i + 1) + "", x, y);
			x = Gobangutil.OFFSET + Gobangutil.INTERVAL * i - w;
			y = Gobangutil.OFFSET + Gobangutil.INTERVAL * Gobangutil.LINE_COUNT;
			g2d.drawString((char) ((int) ('A') + i) + "", x, y);
		}
	}

	private void duawtips(Graphics2D g2d, int x, int y) {
		g2d.setColor(Color.RED);
		x = x * Gobangutil.INTERVAL + Gobangutil.OFFSET;
		y = y * Gobangutil.INTERVAL + Gobangutil.OFFSET;
		// ���ϵ���
		g2d.drawLine(x - Gobangutil.INTERVAL / 2, y - Gobangutil.INTERVAL / 2, x - Gobangutil.INTERVAL / 2,
				y - Gobangutil.INTERVAL / 4);
		// ���ϵ���
		g2d.drawLine(x - Gobangutil.INTERVAL / 2, y - Gobangutil.INTERVAL / 2, x - Gobangutil.INTERVAL / 4,
				y - Gobangutil.INTERVAL / 2);
		// ���µ���
		g2d.drawLine(x - Gobangutil.INTERVAL / 2, y + Gobangutil.INTERVAL / 2, x - Gobangutil.INTERVAL / 4,
				y + Gobangutil.INTERVAL / 2);
		// ���µ���
		g2d.drawLine(x - Gobangutil.INTERVAL / 2, y + Gobangutil.INTERVAL / 2, x - Gobangutil.INTERVAL / 2,
				y + Gobangutil.INTERVAL / 4);
		// ���ϵ���
		g2d.drawLine(x + Gobangutil.INTERVAL / 2, y - Gobangutil.INTERVAL / 2, x + Gobangutil.INTERVAL / 4,
				y - Gobangutil.INTERVAL / 2);
		// ���ϵ���
		g2d.drawLine(x + Gobangutil.INTERVAL / 2, y - Gobangutil.INTERVAL / 2, x + Gobangutil.INTERVAL / 2,
				y - Gobangutil.INTERVAL / 4);
		// ���µ���
		g2d.drawLine(x + Gobangutil.INTERVAL / 2, y + Gobangutil.INTERVAL / 2, x + Gobangutil.INTERVAL / 4,
				y + Gobangutil.INTERVAL / 2);
		// ���µ���
		g2d.drawLine(x + Gobangutil.INTERVAL / 2, y + Gobangutil.INTERVAL / 2, x + Gobangutil.INTERVAL / 2,
				y + Gobangutil.INTERVAL / 4);
		g2d.setColor(Color.BLACK);
	}

	private void drawstar(Graphics2D g2d) {
		// ��Ԫ
		g2d.fillOval(Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 2) - Gobangutil.STAR_WIDTH / 2,
				Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 2) - Gobangutil.STAR_WIDTH / 2,
				Gobangutil.STAR_WIDTH, Gobangutil.STAR_WIDTH);
		// ������
		g2d.fillOval(
				Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 2)
						- (Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 4 + 1)) - Gobangutil.STAR_WIDTH / 2,
				Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 2)
						- (Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 4 + 1)) - Gobangutil.STAR_WIDTH / 2,
				Gobangutil.STAR_WIDTH, Gobangutil.STAR_WIDTH);
		// ������
		g2d.fillOval(
				Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 2)
						- (Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 4 + 1)) - Gobangutil.STAR_WIDTH / 2,
				Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 2)
						+ (Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 4 + 1)) - Gobangutil.STAR_WIDTH / 2,
				Gobangutil.STAR_WIDTH, Gobangutil.STAR_WIDTH);
		// ���Ͻ�
		g2d.fillOval(
				Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 2)
						+ (Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 4 + 1)) - Gobangutil.STAR_WIDTH / 2,
				Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 2)
						- (Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 4 + 1)) - Gobangutil.STAR_WIDTH / 2,
				Gobangutil.STAR_WIDTH, Gobangutil.STAR_WIDTH);
		// ���½�
		g2d.fillOval(
				Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 2)
						+ (Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 4 + 1)) - Gobangutil.STAR_WIDTH / 2,
				Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 2)
						+ (Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT / 4 + 1)) - Gobangutil.STAR_WIDTH / 2,
				Gobangutil.STAR_WIDTH, Gobangutil.STAR_WIDTH);
	}

	private void drawline(Graphics2D g2d) {
		for (int i = 0; i < Gobangutil.LINE_COUNT; i++) {
			g2d.drawLine(Gobangutil.OFFSET, Gobangutil.OFFSET + Gobangutil.INTERVAL * i,
					Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT - 1),
					Gobangutil.OFFSET + Gobangutil.INTERVAL * i);
			g2d.drawLine(Gobangutil.OFFSET + Gobangutil.INTERVAL * i, Gobangutil.OFFSET,
					Gobangutil.OFFSET + Gobangutil.INTERVAL * i,
					Gobangutil.OFFSET + Gobangutil.INTERVAL * (Gobangutil.LINE_COUNT - 1));
		}
	}

	private MouseMotionAdapter mousemoveevent = new MouseMotionAdapter() {
		@Override
		public void mouseMoved(MouseEvent e) {
			curx = (e.getX() - Gobangutil.OFFSET / 2) / Gobangutil.INTERVAL;
			cury = (e.getY() - Gobangutil.OFFSET / 2) / Gobangutil.INTERVAL;
			if (curx >= 0 && curx < Gobangutil.LINE_COUNT && cury >= 0 && cury < Gobangutil.LINE_COUNT) {
				repaint();
			}
		}
	};
	private MouseAdapter mouselistener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if (isGamestart&&!WIN) {
				if (mode == renrenMode) {
					x = (e.getX() - Gobangutil.OFFSET / 2) / Gobangutil.INTERVAL;
					y = (e.getY() - Gobangutil.OFFSET / 2) / Gobangutil.INTERVAL;
					if (x >= 0 & x < Gobangutil.LINE_COUNT && y >= 0 && y < Gobangutil.LINE_COUNT) {
						if (chess1[curx][cury].isempty()) {
							chess1[curx][cury].setOrdernum(count);
							chess1[curx][cury].setPlayer(currentplayer);
							currentplayer = 3 - currentplayer;
							count++;
						}
					}
					Boolean iswin = checkwin(x, y, 3 - currentplayer);
					if (iswin) {
						repaint();
						JOptionPane.showMessageDialog(Gobangpanel.this,
								((currentplayer == WHITE) ? "black" : "white") + "  win");
					}
				} else if (mode == renjimode) {
					// ������
					x = (e.getX() - Gobangutil.OFFSET / 2) / Gobangutil.INTERVAL;
					y = (e.getY() - Gobangutil.OFFSET / 2) / Gobangutil.INTERVAL;
					if (x >= 0 & x < Gobangutil.LINE_COUNT && y >= 0 && y < Gobangutil.LINE_COUNT) {
						if (chess1[curx][cury].isempty()) {
							chess1[curx][cury].setOrdernum(count);
							chess1[curx][cury].setPlayer(WHITE);
							count++;
						}
						Boolean iswin = checkwin(x, y, WHITE);
						if (iswin) {
							repaint();
							JOptionPane.showMessageDialog(Gobangpanel.this,
									((currentplayer == WHITE) ? "black" : "white") + "  win");
						}
						// ��������
						if (zhinengmode == guzhimode) {// ��ֵ����
							List<chess> sortlist = sortchess(BLACK, chess1);
							chess temp = sortlist.get(sortlist.size()-10);
							for(int i=0;i<sortlist.size();i++)
							System.out.println(sortlist.get(i));
							x = temp.getX();
							y = temp.getY();
							chess1[x][y].setPlayer(BLACK);
							chess1[x][y].setOrdernum(count);
							count++;
							currentplayer=3-currentplayer;
							Boolean iswin1 = checkwin(x, y, BLACK);
							if (iswin1) {
								repaint();
								JOptionPane.showMessageDialog(Gobangpanel.this,
										((currentplayer == WHITE) ? "black" : "white") + "  win");
							}
							
						} else if (zhinengmode == guzhitreemode) {// ��ֵ��������
							
							
					   	}

					}
				}
				repaint();
			} else {
				JOptionPane.showMessageDialog(Gobangpanel.this, "�뿪ʼ����Ϸ");
			}
		}

		protected chess getValueByTree1(int d, int player, chess[][] chessBeans) {
			   
			   chess[][] tmp = clone(chessBeans);
			   
			   List<chess> orderList = sortchess(player, tmp);
			   
			   if(d==depth) {
			    //�ﵽ����ָ����ȣ����������ص�ǰ�����С���ȡ���Ĺ�ֵ��ߵĵ㡣
			    return orderList.get(0);
			   }
			   //������ǰ���������п����λ�ã�����getSortList��
			   for(int i=0; i<orderList.size(); i++) {
				   chess bean = orderList.get(i);
			    
			    if(bean.getSum()>Level.ALIVE_4.score) {
			     //�ҵ�Ŀ��
			     return bean;
			    } else {
			     //���������ģ�����塣�����������������Ͻ�������
			     chessBeans[bean.getX()][bean.getY()].setPlayer(player);
			     return getValueByTree1(d+1, 3-player, tmp);
			    }
			   }
			   return null;
			  }
		
		
		
		
		
		
		private chess[][] clone(chess[][] chessBeans) {
			chess[][] temp=new chess[Gobangutil.LINE_COUNT][Gobangutil.LINE_COUNT];
			for(int i=0;i<Gobangutil.LINE_COUNT;i++)
				for(int j=0;j<Gobangutil.LINE_COUNT;j++)
					temp[i][j]=chessBeans[i][j];
			return temp;
		}

		private List<chess> sortchess(int player, chess[][] chesstmp) {
			List<chess> templist = new ArrayList<>();
			for (int i = 0; i < Gobangutil.LINE_COUNT; i++) {
				for (int j = 0; j < Gobangutil.LINE_COUNT; j++) {
					if (chesstmp[i][j].isempty()) {
						int a = getValue(chesstmp[i][j], chesstmp, player);
						int o = getValue(chesstmp[i][j], chesstmp, 3 - player);
						chesstmp[i][j].setAttack(a);
						chesstmp[i][j].setOffence(o);
						chesstmp[i][j].setSum(a + o);
						templist.add(chesstmp[i][j]);   
					}
				}
			}
			Collections.sort(templist);
			return templist;
		}

		private int getValue(chess chess, chess[][] chesstmp, int player) {
			// ��
			String left1 = getStrSeq(chess, -1, 0, chesstmp);
			String right1 = getStrSeq(chess, 1, 0, chesstmp);
			String s1 = left1 + player + right1;
			System.out.println(s1);
			// ��
			String left2 = getStrSeq(chess, 0, -1, chesstmp);
			String right2 = getStrSeq(chess, 0, 1, chesstmp);
			String s2 = left2 + player + right2;
			// Ʋ
			String left3 = getStrSeq(chess, -1, -1, chesstmp);
			String right3 = getStrSeq(chess, 1, 1, chesstmp);
			String s3 = left3 + player + right3;
			// ��
			String left4 = getStrSeq(chess, -1, 1, chesstmp);
			String right4 = getStrSeq(chess, 1, -1, chesstmp);
			String s4 = left4 + player + right4;
			Level l1 = getlevel(s1, player);
			Level l2 = getlevel(s2, player);
			Level l3 = getlevel(s3, player);
			Level l4 = getlevel(s4, player);
			int score = levelscore(l1, l2, l3, l4) + position[chess.getX()][chess.getY()];
			return score;

		}

		private int levelscore(Level l1, Level l2, Level l3, Level l4) {
			int[] levelCount = new int[Level.values().length];
			for (int i = 0; i < levelCount.length; i++) {
				levelCount[i] = 0;
			}
			levelCount[l1.index]++;
			levelCount[l2.index]++;
			levelCount[l3.index]++;
			levelCount[l4.index]++;
			int score = 0;
			if (levelCount[Level.GO_4.index] >= 2
					|| levelCount[Level.GO_4.index] >= 1 && levelCount[Level.ALIVE_3.index] >= 1)// ˫��4����4����
				score = 10000;
			else if (levelCount[Level.ALIVE_3.index] >= 2)// ˫��3
				score = 5000;
			else if (levelCount[Level.SLEEP_3.index] >= 1 && levelCount[Level.ALIVE_3.index] >= 1)// ��3��3
				score = 1000;
			else if (levelCount[Level.ALIVE_2.index] >= 2)// ˫��2
				score = 100;
			else if (levelCount[Level.SLEEP_2.index] >= 1 && levelCount[Level.ALIVE_2.index] >= 1)// ��2��2
				score = 10;
			return Math.max(l1.score, Math.max(l2.score, Math.max(l3.score, Math.max(l4.score, score))));
		}

		private Level getlevel(String str, int player) {
			for (Level level : Level.values()) {
				Pattern pattern = Pattern.compile(level.regex[player - 1]);
				Matcher matcher = pattern.matcher(str);
				String rstr = new StringBuffer(str).reverse().toString();
				boolean r1 = matcher.find();
				pattern.matcher(rstr);
				boolean r2 = matcher.find();
				if (r1 || r2) {
					return level;
				}
			}
			return Level.NULL;
		}

		private String getStrSeq(chess chess, int dx, int dy, chess[][] chesstmp) {
			boolean re = false;
			if (dx < 0 || dx == 0 && dy == -1) {
				re = true;
			}
			String s = "";
			int x = chess.getX();
			int y = chess.getY();
			for (int i = 1; i <= 4; i++) {
				if (x + dx * i >= 0 && x + dx * i < Gobangutil.LINE_COUNT && y + dy * i >= 0
						&& y + dy * i < Gobangutil.LINE_COUNT)
					if (re) {
						s = chesstmp[x + dx * i][y + dy * i].getPlayer() + s;
					} else {
						s = s + chesstmp[x + dx * i][y + dy * i].getPlayer();
					}
			}
			// System.out.println("s");
			return s;
		}

		private Boolean checkwin(int x, int y, int currentplayer) {
			if ((check(x, y, 1, 0, currentplayer) + check(x, y, -1, 0, currentplayer) + 1) > 4
					|| (check(x, y, 0, 1, currentplayer) + check(x, y, 0, -1, currentplayer) + 1) > 4
					|| (check(x, y, -1, 1, currentplayer) + check(x, y, 1, -1, currentplayer) + 1) > 4
					|| (check(x, y, 1, 1, currentplayer) + check(x, y, -1, -1, currentplayer) + 1) > 4) {
				WIN=true;
				return true;
			}
			else
				return false;
		}

		private int check(int x, int y, int dx, int dy, int currentplayer) {
			int count = 0;
			for (int i = 0; i < 4; i++) {
				x = x + dx;
				y = y + dy;
				if (x < Gobangutil.LINE_COUNT && x >= 0 && y < Gobangutil.LINE_COUNT && y >= 0
						&& chess1[x][y].getPlayer() == currentplayer)
					count++;
				else
					break;
			}
			return count;
		}

	};

void undo() {
		// ����
		chess tmp = getcurchess();
		if (count > 1) {
			if(mode==renrenMode){
			currentplayer = chess1[tmp.getX()][tmp.getY()].getPlayer();
			chess1[tmp.getX()][tmp.getY()].setPlayer(EMPTY);
			chess1[tmp.getX()][tmp.getY()].setOrdernum(0);
			count--;
			repaint();
			}else if(mode==renjimode)
			{
				currentplayer = chess1[tmp.getX()][tmp.getY()].getPlayer();
				chess1[tmp.getX()][tmp.getY()].setPlayer(EMPTY);
				chess1[tmp.getX()][tmp.getY()].setOrdernum(0);
				count--;
				currentplayer=3-currentplayer;
				chess tmp1 = getcurchess();
				currentplayer = chess1[tmp1.getX()][tmp1.getY()].getPlayer();
				chess1[tmp1.getX()][tmp1.getY()].setPlayer(EMPTY);
				chess1[tmp1.getX()][tmp1.getY()].setOrdernum(0);
				count--;
				repaint();
			}
		}
	}

	public void changeshoworder() {
		// �����Ƿ���ʾ˳������
		isshoworder = !isshoworder;
		repaint();
	}

	public void newset() {
		fillchess();
		repaint();
		count = 1;
		currentplayer = 1;
		// isGamestart=true;
              if(canjumode==0) {}	
               else if(canjumode==1) {		System.out.println("panduaning");
				for (int i = 0; i < Gobangutil.LINE_COUNT; i++)
					for (int j = 0; j < Gobangutil.LINE_COUNT; j++)
						if (canjudata.build1[i][j] == 1) {
							chess1[i][j].setPlayer(BLACK);
							chess1[i][j].setOrdernum(count);
							count++;
						} else if (canjudata.build1[i][j] == 2) {
							chess1[i][j].setPlayer(WHITE);
							chess1[i][j].setOrdernum(count);
							count++;
                          }
		}
		
		repaint();
	}

	public void setCanjumode(int canjumode) {
		this.canjumode = canjumode;
	}

	public void setGamestart(boolean isGamestart) {
		this.isGamestart = isGamestart;
		WIN=false;
	}

	public void modechange(boolean bool) {
		mode = bool;
		if (mode == renjimode) {
			newset();
			System.out.println("static");
			chess1[Gobangutil.LINE_COUNT / 2][Gobangutil.LINE_COUNT / 2].setPlayer(BLACK);
			chess1[Gobangutil.LINE_COUNT / 2][Gobangutil.LINE_COUNT / 2].setX(Gobangutil.LINE_COUNT / 2);
			chess1[Gobangutil.LINE_COUNT / 2][Gobangutil.LINE_COUNT / 2].setY(Gobangutil.LINE_COUNT / 2);
			chess1[Gobangutil.LINE_COUNT / 2][Gobangutil.LINE_COUNT / 2].setOrdernum(1);
			count++;
			System.out.println("count"+count);
			currentplayer = 3 - currentplayer;
			repaint();
		} else {
			newset();
			repaint();
		}
	}

	public void zhinengchange(boolean b) {
		zhinengmode = b;
	}

	public static enum Level {
		CON_5("����", 0, new String[] { "11111", "22222" }, 100000), ALIVE_4("����", 1, new String[] { "011110", "022220" },
				10000), GO_4("����", 2, new String[] { "011112|0101110|0110110", "022221|0202220|0220220" }, 500), DEAD_4(
						"����", 3, new String[] { "211112", "122221" },
						-5), ALIVE_3("����", 4, new String[] { "01110|010110", "02220|020220" }, 200), SLEEP_3("����", 5,
								new String[] { "001112|010112|011012|10011|10101|2011102",
										"002221|020221|022021|20022|20202|1022201" },
								50), DEAD_3("����", 6, new String[] { "21112", "12221" }, -5), ALIVE_2("���", 7,
										new String[] { "00110|01010|010010", "00220|02020|020020" },
										5), SLEEP_2("�߶�", 8,
												new String[] { "000112|001012|010012|10001|2010102|2011002",
														"000221|002021|020021|20002|1020201|1022001" },
												3), DEAD_2("����", 9, new String[] { "2112", "1221" },
														-5), NULL("null", 10, new String[] { "", "" }, 0);
		private String name;
		private int index;
		private String[] regex;// ������ʽ
		int score;// ��ֵ

		// ���췽��
		private Level(String name, int index, String[] regex, int score) {
			this.name = name;
			this.index = index;
			this.regex = regex;
			this.score = score;
		}

		// ���Ƿ���
		@Override
		public String toString() {
			return this.name;
		}
	};

	// ����
	private static enum Direction {
		HENG, SHU, PIE, NA
	};

	// λ�÷�
	private static int[][] position = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, { 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0 },
			{ 0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0 }, { 0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0 },
			{ 0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0 }, { 0, 1, 2, 3, 4, 5, 6, 6, 6, 5, 4, 3, 2, 1, 0 },
			{ 0, 1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1, 0 }, { 0, 1, 2, 3, 4, 5, 6, 6, 6, 5, 4, 3, 2, 1, 0 },
			{ 0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0 }, { 0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0 },
			{ 0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0 }, { 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0 },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
}
