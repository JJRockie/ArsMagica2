package am2.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class InventoryRuneBag implements IInventory{
	public static final int inventorySize = 16;
	private ItemStack[] inventoryItems;

	public InventoryRuneBag(){
		inventoryItems = new ItemStack[inventorySize];
	}

	public void SetInventoryContents(ItemStack[] inventoryContents){
		int loops = Math.min(inventorySize, inventoryContents.length);
		for (int i = 0; i < loops; ++i){
			inventoryItems[i] = inventoryContents[i];
		}
	}

	@Override
	public int getSizeInventory(){
		return inventorySize;
	}

	@Override
	public ItemStack getStackInSlot(int i){
		if (i < 0 || i > inventoryItems.length - 1){
			return null;
		}
		return inventoryItems[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j){

		if (inventoryItems[i] != null){
			if (inventoryItems[i].stackSize <= j){
				ItemStack itemstack = inventoryItems[i];
				inventoryItems[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventoryItems[i].splitStack(j);
			if (inventoryItems[i].stackSize == 0){
				inventoryItems[i] = null;
			}
			return itemstack1;
		}else{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack){
		inventoryItems[i] = itemstack;
	}

	@Override
	public String getName(){
		return "Rune Bag";
	}

	@Override
	public int getInventoryStackLimit(){
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer){
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player){
	}

	@Override
	public void closeInventory(EntityPlayer player){
	}

	public ItemStack[] GetInventoryContents(){
		return inventoryItems;
	}

	@Override
	public ItemStack removeStackFromSlot(int i){
		if (inventoryItems[i] != null){
			ItemStack itemstack = inventoryItems[i];
			inventoryItems[i] = null;
			return itemstack;
		}else{
			return null;
		}
	}

	@Override
	public boolean hasCustomName(){
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack){
		return false;
	}

	@Override
	public void markDirty(){
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
