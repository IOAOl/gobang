package Gobang;

public class chess implements Comparable<chess>{
	private int x;
	private int y;
	private int ordernum;
	//1代表黑子，2代表白子
	private int player=1;
	private final int EMPTY =0;
	private int offence;
	private int attack;
	private int sum;
	public chess(int x, int y, int ordernum, int player) {
		super();
		this.x = x;
		this.y = y;
		this.ordernum = ordernum;
		this.player = player;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	public int getPlayer() {
		return player;
	}
	public void setPlayer(int player) {
		this.player = player;
	}	
	public Boolean isempty()
	{
		if(player==EMPTY)
		{
			return true;
		}else
		{
			return false;
		}
	}
	@Override
	public int compareTo(chess c1) {
		sum=attack+offence;
		int sum=c1.attack+c1.offence;
		if(this.sum>sum)
			return 1;
		else if(this.sum<sum) {
			return -1;
		}else
			return 0;
	}
	public int getOffence() {
		return offence;
	}
	public void setOffence(int offence) {
		this.offence = offence;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getEMPTY() {
		return EMPTY;
	}
}
