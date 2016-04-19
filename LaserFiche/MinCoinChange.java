public class MinCoinChange {
	

	public int minCoinDynamic(int amount, int[] coins) {
		int[] coinReq = new int[amount+1]; // this will store the optimal solution
											// for all the values -- from 0 to
											// given amount.
		int[] CC = new int[coins.length]; // resets for every smaller problems
											// and minimum in it is the optimal
											// solution for the smaller problem.
		coinReq[0] = 0; // 0 coins are required to make the change for 0
		// now solve for all the amounts from 1 to amount
		// this we will want to start from the difference of amounts of our given and already calculated
		for (int amt = 1; amt <= amount; amt++) 
		{
			//reinitialize our array for holding our counts
			for (int j = 0; j < CC.length; j++) 
			{
				CC[j] = -1;
			}
			
			// Now try taking every coin one at a time and fill the solution in
			// the CC[]
			for (int j = 0; j < coins.length; j++) 
			{
				System.out.println(coins[j] + " <= " + amt);
				// check if coin value is less than amount
				if (coins[j] <= amt) 
				{ 
					//find an already calc number and use its already calculated value and add 1
					CC[j] = coinReq[amt - coins[j]] + 1; // if available,
																// select the
																// coin and add
																// 1 to solution
																// of
																// (amount-coin value)
					System.out.println("For " + amt + " amt we have " + CC[j]);
				}
				//System.out.println("For " + j + " amt we have " + CC[j]);
			}
			
			//Now solutions for amt using all the coins is stored in CC[]
			//take out the minimum (optimal) and store in coinReq[amt]
			
			coinReq[amt] = -1;
			
			for(int j=1; j<CC.length; j++)
			{
				//System.out.println(amt + " Checking " + CC[j]);
				//checks that the what we find hasnt been found yet
				if(CC[j] > 0 && (coinReq[amt] == -1 || coinReq[amt] > CC[j]))
				{
					coinReq[amt]=CC[j];
					//System.out.println("Found Minimum : " + CC[j]);
				}
			}
		}

		for(int i = 0; i < coinReq.length; i++)
		{
			System.out.println("Value for " + i + " is " + coinReq[i]);
		}
		//return the optimal solution for amount
		return coinReq[amount];
		
	}

	public static void main(String[] args) {
		int[] coins = { 90, 30, 24, 10, 6, 2, 1 }; //{ 1, 2, 3 };
		int amount = 18;
		MinCoinChange m = new MinCoinChange();		
		System.out.println("(Dynamic Programming) Minimum Coins required to make change for "
				+ amount + " are: " + m.minCoinDynamic(amount, coins));
	}

}