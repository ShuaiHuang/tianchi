package my.group.mapreduce;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.Reducer;

import java.io.IOException;
import java.util.*;

/**
 * Reducer模板。请用真实逻辑替换模板内容
 */
public class MyReducer implements Reducer {
    private Record Result;

    public void setup(TaskContext context) throws IOException {
        Result = context.createOutputRecord();
    }

    public void reduce(Record key, Iterator<Record> values, TaskContext context) throws IOException {
        long item_l1_buy = 0, item_l3_buy = 0, item_l7_buy = 0, item_l14_buy = 0;
        long item_l1_clk = 0, item_l3_clk = 0, item_l7_clk = 0, item_l14_clk = 0;
        long item_l1_distinct_buy = 0, item_l3_distinct_buy = 0,
        		item_l7_distinct_buy = 0, item_l14_distinct_buy = 0;
        long item_l1_distinct_clk = 0, item_l3_distinct_clk = 0,
        		item_l7_distinct_clk = 0, item_l14_distinct_clk = 0;
        long item_l_buy_date = 0, item_l_clk_date = 0;
        long item_ifrebuy = 0, item_distinct_rebuy = 0;
        
        Hashtable<String,Long> BuyUser_l1 = new Hashtable<String,Long>();
        Hashtable<String,Long> BuyUser_l3 = new Hashtable<String,Long>();
        Hashtable<String,Long> BuyUser_l7 = new Hashtable<String,Long>();
        Hashtable<String,Long> BuyUser_l14 = new Hashtable<String,Long>();
        Hashtable<String,Long> ClkUser_l1 = new Hashtable<String,Long>();
        Hashtable<String,Long> ClkUser_l3 = new Hashtable<String,Long>();
        Hashtable<String,Long> ClkUser_l7 = new Hashtable<String,Long>();
        Hashtable<String,Long> ClkUser_l14 = new Hashtable<String,Long>();
        
        final int LAST_DAY = 31;
        
        while (values.hasNext()) {
            Record CurRecord = values.next();
            
            long Date = CurRecord.getBigint("event_date");
            long BehavType = CurRecord.getBigint("behavior_type");
            String UserId = CurRecord.getString("user_id");
            
            if(Date<=LAST_DAY){
            	if(BehavType==4){
                	//item_ln_buy
                	if(Date==LAST_DAY){
                    	item_l1_buy+=1;
                    	item_l3_buy+=1;
                    	item_l7_buy+=1;
                    	item_l14_buy+=1;
                    	
                    	if(!BuyUser_l1.containsKey(UserId)){
                    		BuyUser_l1.put(UserId, 1L);
                    		BuyUser_l3.put(UserId, 1L);
                    		BuyUser_l7.put(UserId, 1L);
                    		BuyUser_l14.put(UserId, 1L);
                    	}
                    	else{
                    		Long Val = BuyUser_l1.get(UserId);
                    		BuyUser_l1.put(UserId, Val+1);
                    		BuyUser_l3.put(UserId, Val+1);
                    		BuyUser_l7.put(UserId, Val+1);
                    		BuyUser_l14.put(UserId, Val+1);
                    	}
                    }
                	else if(Date<LAST_DAY && Date>=LAST_DAY-2){
                		item_l3_buy+=1;
                		item_l7_buy+=1;
                		item_l14_buy+=1;
                		
                		if(!BuyUser_l3.containsKey(UserId)){
                    		BuyUser_l3.put(UserId, 1L);
                    		BuyUser_l7.put(UserId, 1L);
                    		BuyUser_l14.put(UserId, 1L);
                    	}
                    	else{
                    		Long Val = BuyUser_l3.get(UserId);
                    		BuyUser_l3.put(UserId, Val+1);
                    		BuyUser_l7.put(UserId, Val+1);
                    		BuyUser_l14.put(UserId, Val+1);
                    	}
                	}
                	else if(Date<LAST_DAY-2 && Date>=LAST_DAY-6){
                		item_l7_buy+=1;
                		item_l14_buy+=1;
                		
                		if(!BuyUser_l7.containsKey(UserId)){
                    		BuyUser_l7.put(UserId, 1L);
                    		BuyUser_l14.put(UserId, 1L);
                    	}
                    	else{
                    		Long Val = BuyUser_l7.get(UserId);
                    		BuyUser_l7.put(UserId, Val+1);
                    		BuyUser_l14.put(UserId, Val+1);
                    	}
                	}
                	else if(Date<LAST_DAY-6 && Date>=LAST_DAY-13){
                		item_l14_buy+=1;
                		
                		if(!BuyUser_l14.containsKey(UserId)){
                    		BuyUser_l14.put(UserId, 1L);
                    	}
                    	else{
                    		Long Val = BuyUser_l14.get(UserId);
                    		BuyUser_l14.put(UserId, Val+1);
                    	}
                	}
                	
                	//item_l_buy_date
                	if(Date>item_l_buy_date){
                		item_l_buy_date=Date;
                	}
                }
                else if(BehavType==1){
                	//item_ln_clk
                	if(Date==LAST_DAY){
                    	item_l1_clk+=1;
                    	item_l3_clk+=1;
                    	item_l7_clk+=1;
                    	item_l14_clk+=1;
                    	
                    	if(!ClkUser_l1.containsKey(UserId)){
                    		ClkUser_l1.put(UserId, 1L);
                    		ClkUser_l3.put(UserId, 1L);
                    		ClkUser_l7.put(UserId, 1L);
                    		ClkUser_l14.put(UserId, 1L);
                    	}
                    	else{
                    		Long Val = ClkUser_l1.get(UserId);
                    		ClkUser_l1.put(UserId, Val+1);
                    		ClkUser_l3.put(UserId, Val+1);
                    		ClkUser_l7.put(UserId, Val+1);
                    		ClkUser_l14.put(UserId, Val+1);
                    	}
                    }
                	else if(Date<LAST_DAY && Date>=LAST_DAY-2){
                		item_l3_clk+=1;
                		item_l7_clk+=1;
                		item_l14_clk+=1;
                		
                		if(!ClkUser_l3.containsKey(UserId)){
                    		ClkUser_l3.put(UserId, 1L);
                    		ClkUser_l7.put(UserId, 1L);
                    		ClkUser_l14.put(UserId, 1L);
                    	}
                    	else{
                    		Long Val = ClkUser_l3.get(UserId);
                    		ClkUser_l3.put(UserId, Val+1);
                    		ClkUser_l7.put(UserId, Val+1);
                    		ClkUser_l14.put(UserId, Val+1);
                    	}
                	}
                	else if(Date<LAST_DAY-2 && Date>=LAST_DAY-6){
                		item_l7_clk+=1;
                		item_l14_clk+=1;
                		
                		if(!ClkUser_l7.containsKey(UserId)){
                    		ClkUser_l7.put(UserId, 1L);
                    		ClkUser_l14.put(UserId, 1L);
                    	}
                    	else{
                    		Long Val = ClkUser_l7.get(UserId);
                    		ClkUser_l7.put(UserId, Val+1);
                    		ClkUser_l14.put(UserId, Val+1);
                    	}
                	}
                	else if(Date<LAST_DAY-6 && Date>=LAST_DAY-13){
                		item_l14_clk+=1;
                		
                		if(!ClkUser_l14.containsKey(UserId)){
                    		ClkUser_l14.put(UserId, 1L);
                    	}
                    	else{
                    		Long Val = ClkUser_l14.get(UserId);
                    		ClkUser_l14.put(UserId, Val+1);
                    	}
                	}
                	
                	//item_l_clk_date
                	if(Date>item_l_clk_date){
                		item_l_clk_date=Date;
                	}
                }
            }
        }
        
        //
        item_l_clk_date = LAST_DAY - item_l_clk_date + 1;
        item_l_buy_date = LAST_DAY - item_l_buy_date + 1;
        
        //item_ln_distinct_buy
    	item_l1_distinct_buy = BuyUser_l1.size();
    	item_l3_distinct_buy = BuyUser_l3.size();
    	item_l7_distinct_buy = BuyUser_l7.size();
    	item_l14_distinct_buy = BuyUser_l14.size();
    	
    	//item_ln_distinct_clk
    	item_l1_distinct_clk = ClkUser_l1.size();
    	item_l3_distinct_clk = ClkUser_l3.size();
    	item_l7_distinct_clk = ClkUser_l7.size();
    	item_l14_distinct_clk = ClkUser_l14.size();
    	
    	//item_ifrebuy
    	item_ifrebuy = item_l14_buy-item_l14_distinct_buy>0 ? 1 : 0;
    	
    	//item_distinct_rebuy
    	if(item_ifrebuy == 1){
    		Enumeration<Long> BuyTimes = BuyUser_l14.elements();
    		while(BuyTimes.hasMoreElements()){
    			Long CurVal = BuyTimes.nextElement();
    			if(CurVal>1){
    				item_distinct_rebuy += 1;
    			}
    		}
    	}
        
        Result.set(0, key.get("item_id"));
        Result.set(1, item_l1_buy);
        Result.set(2, item_l3_buy/3);
        Result.set(3, item_l7_buy/7);
        Result.set(4, item_l14_buy/14);
        Result.set(5, item_l1_clk);
        Result.set(6, item_l3_clk/3);
        Result.set(7, item_l7_clk/7);
        Result.set(8, item_l14_clk/14);
        Result.set(9,item_l1_distinct_buy);
        Result.set(10,item_l3_distinct_buy);
        Result.set(11,item_l7_distinct_buy);
        Result.set(12,item_l14_distinct_buy);
        Result.set(13,item_l1_distinct_clk);
        Result.set(14,item_l3_distinct_clk);
        Result.set(15,item_l7_distinct_clk);
        Result.set(16,item_l14_distinct_clk);
        Result.set(17,item_l_buy_date);
        Result.set(18,item_l_clk_date);
        Result.set(19,item_ifrebuy);
        Result.set(20,item_distinct_rebuy);
        context.write(Result);
    }

    public void cleanup(TaskContext arg0) throws IOException {

    }
}
