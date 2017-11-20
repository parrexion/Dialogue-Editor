package senseDialogueEditor.data;

import java.util.ArrayList;
import java.util.List;

public class Enemy {

	public enum WaitStates {MOVE,CHASE,FLEE,RANGE}
	
	public String name = "";
	public int maxhp = 1;
	public float speedX = 0f;
	public float speedY = 0f;

	//[Header("AI Values")]
	public List<WaitStates> waitStates = new ArrayList<WaitStates>();
	//[MinMaxRangeAttribute(0.1f,10.0f)]
	public float waitTimeLimitMin = 3f;
	public float waitTimeLimitMax = 5f;

	//[Range(0.5f,20.0f)]
	public float chaseTimeLimit = 30f;

	//[Range(1.0f,10.0f)]
	public float fleeDistance = 3f;
	//[Range(0.5f,3.0f)]
	public float fleeTimeLimit = 30f;

//	[Header("Attacking")]
//	[Range(0.5f,3.0f)]
	public float meleeRange = 1f;
//	[Range(0.1f,10.0f)]
	public float attackRate = 1f;
	public int attacks = 1;
	public float meleeTimeStartup = 0f;
	public float meleeTimeAnimation = 1f;

//	[Header("Reward")]
	public int exp = 0;
	public int money = 0;
	//Add some kind of loot table
}
