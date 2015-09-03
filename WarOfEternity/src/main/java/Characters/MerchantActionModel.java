package Characters;

import Items.Item;
import Map.Area;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author Vasilis Triantaris
 */
public class MerchantActionModel {

    private String verbPart;
    private String nounPart;
    private List<Item> listOfGameItems;
    private List<Area> listOfGameAreas;
    private List<Merchant> listOfGameMerchants;
    
    //Constructor
    public MerchantActionModel(String verb, String noun, List<Item> items, List<Area> areas, List<Merchant> merchants){
        this.verbPart = verb;
        this.nounPart = noun;
        this.listOfGameItems = items;
        this.listOfGameAreas = areas;
        this.listOfGameMerchants = merchants;
    }

    /**
     * This method controlles the reading of items for the merchant that will
     * be displayed to the player after a talk to merchant action command.
     * 
     * @return Returns the inventory of the merchant analytically written object by object if everything goes wright or else a message that describes the result of the action command.
     */
    public String SetMerchantItemListToBeDisplayed(Player player){
   
        String resultMessage = "";
        JSONObject jObj = this.PlayersLocationCanStartATransaction(player.GetAreaLocation().GetAreasName());
        
        if(!(boolean) jObj.get("status"))
            return (String) jObj.get("message");
        
        
        
        /*
        String message = "";
        String locationIntegrity = this.playersLocationCanStartATransaction(merchantObj, player);
        
       
        if(!locationIntegrity.equals(""))
            return locationIntegrity;
        
        //If the user is starting the transaction with the merchant
        if((this.verbPartOfCommand.equalsIgnoreCase("talk")) || (this.verbPartOfCommand.equalsIgnoreCase("bargain"))){
            if(merchantObj.GetMerchantGoodsList().isEmpty())
                this.SetMerchantGoods(merchantObj, gameItemList);
            
            message = "How can i help you sir?, I have got the best stuff for defence and offence. So what will it be?\n\n";
            
            for(Item merchGood : merchantObj.GetMerchantGoodsList()){
                //Depending on the type of item it processed the message
                switch(merchGood.GetItemType()){
                    case 1 :
                        message += "Name : "+merchGood.GetItemName()+"\nDescription : "+merchGood.GetItemDescription()+
                                "\nHealing Power : "+merchGood.GetItemHealingPower()+"\nItem Quantity : 8"+
                                "\nItem Weight : "+merchGood.GetItemWeight()+" kg\nCost : "+merchGood.GetItemValueInGold()+
                                " gold\n---------------------------------------------\n";   
                    break;
                    
                    case 3 :
                        message += "Name : "+merchGood.GetItemName()+"\nDescription : "+merchGood.GetItemDescription()+
                                "\nDamage : "+merchGood.GetItemValue()+"\nItem Weight : "+merchGood.GetItemWeight()+
                                " kg\nCost : "+merchGood.GetItemValueInGold()+" gold\n---------------------------------------------\n";   
                    break;
                    
                    case 6 :
                    case 5 :
                        message += "Name : "+merchGood.GetItemName()+"\nDescription : "+merchGood.GetItemDescription()+
                                "\nArmor : "+merchGood.GetItemValue()+"\nItem Weight : "+merchGood.GetItemWeight()+
                                " kg\nCost : "+merchGood.GetItemValueInGold()+" gold\n---------------------------------------------\n";   
                    break;  
                } 
            }
        }
        else if(this.verbPartOfCommand.equalsIgnoreCase("buy")){
            message = merchantObj.MerchantBuyItemProcess(player, this.nounPartOfCommand);
        }
        else if(this.verbPartOfCommand.equalsIgnoreCase("sell")){
            message = merchantObj.MerchantSellItemProcess(player, this.nounPartOfCommand);
        }
        */

        return "";
    }
    
     
    /**
     * Method that checks if the location of the player is at the same of the merchants
     * so it can start a transaction.
     * 
     * @return Returns a message if the user is trying to start a transaction in a area with no merchants. If there is a merchant then it returns an empty message.
     */
    private JSONObject PlayersLocationCanStartATransaction(String playersName){
        
        JSONObject jObj = new JSONObject();
        String message = "There is no merchant in this area!";
        boolean status = false;
        
        for(Merchant eachGameMerchant : this.listOfGameMerchants){
            if(eachGameMerchant.GetAreaLocation().GetAreasName().equals(playersName)){
                message = "";
                status = true;
            }
        }

        jObj.put("message", message);
        jObj.put("status", status);
        
        return jObj;
    }
    
    /**
     * Method that reads the list of game items and if their type is not equal to 
     * 2 or 4 (key or door) then adds it to merchants goods.
     * 
     * @param merchantObj   The object of the merchant.
     * @param gameItemList  The list of items of the game.
     */
    private void SetMerchantGoods(Merchant merchantObj, List<Item> gameItemList){

        for(Item eachGameItem : gameItemList){
            
            if((eachGameItem.GetItemType() != 2) && (eachGameItem.GetItemType() != 4))
                merchantObj.AddItemToMerchantGoods(eachGameItem);  
        }   
    }
    
    
}
