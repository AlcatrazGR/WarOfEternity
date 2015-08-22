package Characters;

import Items.Item;
import java.util.List;

/**
 * Class MerchantController is the controlling class (since the application is 
 * written in MVC architecture) for actions based on merchant.
 * 
 * @author Vasilis Triantaris
 */
public class MerchantController {
    
    private final String nounPartOfCommand;
    private final String verbPartOfCommand;

    public MerchantController(String nounPart, String verbPart){
        this.nounPartOfCommand = nounPart;
        this.verbPartOfCommand = verbPart;
    }

    
    /**
     * This method is the main controlling method for merchant actions. It calls
     * the competent methods to check the location integrity of the merchant based
     * on the location of the player. Based on the command given decides if the 
     * action is referred to a merchant or a healer / doctor. Initializes the 
     * inventory of the merchant and how that is going to be displayed. And lastly
     * calls the methods of sell and buy if the user action is referred for buying
     * or selling items to the merchant.
     * 
     * @param merchantObj   The object of the merchant.
     * @param player    The object of the player.
     * @param gameItemList  The list of game items.
     * @return Returns the inventory of the merchant analytically written object by object if everything goes wright or else a message that describes the result of the action command.
     */
    public String MerchantMainTransactionController(Merchant merchantObj, Player player, List<Item> gameItemList){
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

        return message;
    }
    
    /**
     * Method that checks if the location of the player is at the same of the merchants
     * so it can start a transaction.
     * 
     * @param merchantObj   The object of the merchant.
     * @param player    The object of the player.
     * @return Returns a message if the user is trying to start a transaction in a area with no merchants. If there is a merchant then it returns an empty message.
     */
    private String playersLocationCanStartATransaction(Merchant merchantObj, Player player){
        String message = "";
        
        if(!merchantObj.GetAreaLocation().GetAreasName().equals(player.GetAreaLocation().GetAreasName()))
                message = "There is no merchant in this area!";
        
        return message;
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
