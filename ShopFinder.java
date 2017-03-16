import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author vishal
 *
 */
public class ShopFinder {

	/**
	 * @param args
	 */
	ArrayList<HashMap<String, ItemDetails>> ShoppingMall = new ArrayList<HashMap<String, ItemDetails>> (100);
	public static void main(String[] args) 
	{
		//System.out.println(args[0]+" "+args[1]+" "+args[2]);
		ShopFinder finder = new ShopFinder();
		finder.readPriceList(args[0]);
		
		finder.findShop(args);
	}
	
	public void readPriceList(String csvFile)
	{
		HashMap<String, ItemDetails> shopBin;
		 //String csvFile = "/home/vishal/workspace/DataOne/src/data.csv";
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";

	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	            int lineNo = 0;
	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] entry = line.split(cvsSplitBy);	                
	                
	                int shopNo = Integer.valueOf(entry[0])-1;
	                float cost = Float.valueOf(entry[1]);
	                int i=2;
	                //System.out.println("shopNo " + shopNo );
	                if(hasIndex(shopNo))
	                {
	                	//System.out.println("shop exists");
	                	shopBin = ShoppingMall.get(shopNo);
	                }
	                else
	                {
	                	//System.out.println("new shop");
	                	shopBin = new HashMap<String, ItemDetails>();
	                }
	                while(i<entry.length)
	                {
	                	String itemName = entry[i].trim();
	                	shopBin.put(itemName, new ItemDetails(lineNo,cost));
	                	//System.out.println("adding "+itemName+" in "+shopNo);
	                	i++;
	                }
	                if(hasIndex(shopNo))
	                	ShoppingMall.remove(shopNo);
	                
	                ShoppingMall.add(shopNo, shopBin);
	                lineNo++;
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	}
public boolean hasIndex(int index)
{
	try{
	ShoppingMall.get(index);
	return true;
	}
	catch (IndexOutOfBoundsException e) {
		// TODO: handle exception
		return false;
	}
}
	public void findShop(String[] args)
	{
		float minCost = Float.MAX_VALUE;
		int minShopNo=-1;
		
		//assuming that shop numbers are continous and start from 1
		for (int shopNo=0;shopNo<ShoppingMall.size();shopNo++)//HashMap<String, ItemDetails> shopBin : ShoppingMall
		{
			//System.out.println(shopNo);
			HashMap<String, ItemDetails> shopBin = ShoppingMall.get(shopNo);
			//System.out.println(shopNo+" shopBin "+shopBin);
			float totalCost = 0;
			boolean contains=false;
			for(int i=1;i<args.length;i++)
			{
				String itemName = args[i];
				//System.out.println(i+" "+itemName+" "+shopBin.get(itemName));
				if(shopBin.containsKey(itemName))
				{
					//System.out.println(itemName+" "+shopBin.get(itemName).cost);
					contains=true;
					totalCost = totalCost+shopBin.get(itemName).cost;
				}
				else
				{
					contains=false;
					break;
				}
				
			}
			if(contains && totalCost < minCost)
			{
				
				minCost=totalCost;
				minShopNo = shopNo;
				//System.out.println("shop: "+minShopNo+" mincost: "+minCost);
			}
		}
		if(minShopNo==-1)
			System.out.println("none");
		else
			System.out.println(minShopNo+1+", "+minCost);
	}
}

class ItemDetails
{
	int sr;
	float cost;
	
	ItemDetails(int sr,	float cost)
	{
		this.sr = sr;
		this.cost = cost;
	}
}
