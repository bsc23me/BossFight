package me.bsc23me.BossFight2.boss;

public enum BossType {

	IRON_GOLEM(1500);
	
	final int HP;
	
	private BossType(int hp){
		HP = hp;
	}
	
}
