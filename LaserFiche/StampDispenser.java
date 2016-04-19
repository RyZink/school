import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Facilitates dispensing stamps for a postage stamp machine.
 */
public class StampDispenser
{
    public ArrayList<Integer> valuesFound; //this will store all values calculated thus far
    public int[] stampDenominations;
    /**
     * Constructs a new StampDispenser that will be able to dispense the given 
     * types of stamps.
     *
     * @param stampDenominations The values of the types of stamps that the 
     *     machine should have.  Should be sorted in descending order and 
     *     contain at least a 1.
     */
    public StampDispenser(int[] stampDenominations)
    {
        this.stampDenominations = stampDenominations;

        if(!checkDenominationsIsValid(stampDenominations))
        {
            //output a warning and state that the given array is not valid
            System.out.println("Given input is not valid");
            //let them know you will be using an array with only {1}
        }

        valuesFound = newArrayList(stampDenominations[0], -1);
    }

    /**
     * Returns the minimum number of stamps that the machine can dispense to
     * fill the given request.
     *
     * @param request The total value of the stamps to be dispensed.
     */
    public int calcMinNumStampsToFillRequest(int request)
    {
        ArrayList<Integer> possibleCalcs;
        
        //valuesFound = newArrayList(request + 1, -1);
        valuesFound.set(0, 0);
        
        //start looping from 0 to request for calculating number of coins needed
        for(int amount = 1; amount <= request; amount++)
        {
            //create a list to hold solutions for the amount we are looking at
            possibleCalcs = newArrayList(stampDenominations.length, -1);

            //go through all the stamps and find possible solutions
            for(int index = 0; index < stampDenominations.length; index++)
            {
                //we go here and check if we can use the stamp
                if(stampDenominations[index] <= amount)
                {
                    //this will set a location in our possible stamp calcs using
                    //already a calculated optimal solution with the difference of amount - opt
                    int optSolution = valuesFound.get(amount - stampDenominations[index]);
                    
                    possibleCalcs.set(index, optSolution + 1);
                }

            }

            valuesFound.set(amount, -1);
            //now check the solutions found and get the minimum one
            for(int j = 0; j < possibleCalcs.size(); j++)
            {
                //first: check we are grabbing a valid posible solution (-1 is not valid)
                //second: check our saved computed values for an existing value or if our current one is not optimal
                if(possibleCalcs.get(j) > 0 &&
                    (valuesFound.get(amount) == -1 || valuesFound.get(amount) > possibleCalcs.get(j)))
                {
                    valuesFound.set(amount, possibleCalcs.get(j));
                }
            }

        }

        for(int i = 0; i < valuesFound.size(); i++)
        {
            System.out.println(i + "# :" + valuesFound.get(i));
        }
        
        return valuesFound.get(request);
    }

//################################# ADDED FUNCTIONS ###########################################//
    /**
     * Checks to make sure our list of stamps has all the requirements in order
     * for the dispenser to run its function correctly
     * - no duplicates
     * - contains 1 as its last value in the array
     * - is not an empty array list
     * @param  stampDenominations [array of stamp values for the dispenser to use]
     * @return                    [true for valid, false for invalid]
     */
    public boolean checkDenominationsIsValid(int[] stampDenominations)
    {
        int sizeOfArray = stampDenominations.length;
        
        //check to make sure there is no array of size 0 and that 
        // the array contians a 1 cent stamp
        if(sizeOfArray == 0 || stampDenominations[sizeOfArray - 1] != 1)
        {
            return false;
        }

        //loop through and ensure the array has no duplicates and is in descending order
        //NOTE: we only go to length - 1 since there will be nothing to
        //check against the last item in the array
        for(int index = 0; index < sizeOfArray - 1; index++)
        {
            int left = stampDenominations[index];
            int right = stampDenominations[index + 1];
            //check for decending order
            if(!(left > right))
            {
                return false;
            }
        }

        //if gotten to this line we have a valid list of stamps
        return true;
    }

    /**
     * Creates an array lst with the desired size and default values
     * @param  size
     * @param  defaultValue
     * @return returnedList [the new array list]
     */
    private ArrayList<Integer> newArrayList(int size, int defaultValue)
    {
        ArrayList<Integer> returnedList = new ArrayList<>();
        //create an array list of the desired length and value to be initialized with
        for(int index = 0; index < size; index++)
        {
            returnedList.add(defaultValue);
        }

        return returnedList;
    }
 

    
    public static void main(String[] args)
    {
        //testValidArray();

        int[] denominations = { 90, 30, 24, 10, 6, 2, 1 };
        StampDispenser stampDispenser = new StampDispenser(denominations);
        System.out.println(stampDispenser.calcMinNumStampsToFillRequest(18));
        

    }

    public static void testValidArray()
    {
        StampDispenser stampDispenser;

        int[] d1 = new int[0];
        stampDispenser = new StampDispenser(d1);
        System.out.println(
            stampDispenser.checkDenominationsIsValid(d1) == false ? 
            "Pass" : "Fail");
        
        int[] d2 = {10, 9, 8};
        stampDispenser = new StampDispenser(d2);
        System.out.println(
            stampDispenser.checkDenominationsIsValid(d2) == false ? 
            "Pass" : "Fail");

        int[] d3 = {10, 9, 1};
        stampDispenser = new StampDispenser(d3);
        System.out.println(
            stampDispenser.checkDenominationsIsValid(d3) == true ? 
            "Pass" : "Fail");

        int[] d4 = {110, 20, 19, 5, 6, 1};
        stampDispenser = new StampDispenser(d4);
        System.out.println(
            stampDispenser.checkDenominationsIsValid(d4) == false ? 
            "Pass" : "Fail");

        int[] d5 = {110, 110, 20, 20, 6, 1};
        stampDispenser = new StampDispenser(d5);
        System.out.println(
            stampDispenser.checkDenominationsIsValid(d5) == false ? 
            "Pass" : "Fail");
    }
}
