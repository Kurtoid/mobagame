package mobagame.core.game;

public class InGamePlayer extends GameObject{

    private int playerID;
    private Character character;

    private int phyPow;
    private int abiPow;
    private int maxHealth;
    private int maxMana;
    private double speed;
    private int armor;
    private int magicResist;
    private int currentHealth;
    private int currentMana;

    private int avalableUpgrades = 0;
    private int goldAmount = 0;
    private int xp = 0;
    private int levelXp = 0;
    private int xpToNextLevel = 0;
    private int playerLevel = 1;
    public ObjectMover mover;


    //0 = Q, 1 = W, 2 = E, 3 = R
    public int[] abilityLevels = {0, 0, 0, 0};
    public int[] cooldowns = {0, 0, 0, 0};

    public Item[] inventory = {(GameItems.empty), (GameItems.empty), (GameItems.empty), (GameItems.empty),
            (GameItems.empty), (GameItems.empty), (GameItems.empty), (GameItems.empty)};

    public Character getCharacter() {
        return character;
    }

    public InGamePlayer(int playerID, Character character, int xp) {
        this.playerID = playerID;
        setCharacter(character);

        // Not entered by user
        this.playerLevel = 0;
        this.levelXp = 0;
        this.xpEarned(levelXp);
    }

    private void setCharacter(Character character) {
        this.character = character;
        this.currentHealth = maxHealth = character.getBaseMaxHealth();
        this.currentMana = maxMana = character.getBaseMaxMana();
        this.phyPow = character.getBasePhyPow();
        this.abiPow = character.getBaseAbiPow();
        this.speed = character.getSpeed();
        this.armor = character.getBaseArmor();
        this.magicResist = character.getBaseMagicResist();

    }

    public void xpEarned(int xp) {
        this.xp += xp;
        this.levelXp += xp;
        while (this.levelXp >= this.xpToNextLevel) {
            this.levelXp -= this.xpToNextLevel;
            this.levelUp();
        }
    }

    public int getPhyPow() {
        return phyPow;
    }

    public void setPhyPow(int phyPow) {
        this.phyPow = phyPow;
    }

    public int getAbiPow() {
        return abiPow;
    }

    public void setAbiPow(int abiPow) {
        this.abiPow = abiPow;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getMagicResist() {
        return magicResist;
    }

    public void setMagicResist(int magicResist) {
        this.magicResist = magicResist;
    }

    public int getLevel() {
        return playerLevel;
    }

    public ObjectMover getMover() {
        return mover;
    }

    public void setMover(ObjectMover mover) {
        this.mover = mover;
    }

    public Item[] getInventory() {
        return inventory;
    }

    public void setItem(int index, Item item) {
        inventory[index] = item;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public InGamePlayer() {
        speed = 100;
    }

    public InGamePlayer(int playerID, Character chara) {
        this.playerID = playerID;
        this.setCharacter(chara);
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public double getX() {
        return pos.getX();
    }

    public double getY() {
        return pos.getY();
    }

    public int getPlayerID() {
        return playerID;
    }

    public String toString() {
        return  "phyPow = " + phyPow +
                ", abiPow = " + abiPow +
                ", maxHealth = " + maxHealth +
                ", maxMana = " + maxMana +
                ", speed = " + speed +
                ", armor = " + armor +
                ", magicResist =" + magicResist +
                ", currentHealth = " + currentHealth +
                ", currentMana = " + currentMana +
                ", goldAmount = " + goldAmount +
                ", xp = " + xp +
                ", levelXp = " + levelXp +
                ", xpToNextLevel = " + xpToNextLevel +
                ", playerLevel = " + playerLevel +
                ", position = " + pos;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public InGamePlayer(Character character, int currentHealth, int currentMana) {
        this.setCharacter(character);
        this.currentHealth = currentHealth;
        this.currentMana = currentMana;
    }

    public void setX(double x) {
        pos.setLocation(x, pos.getY());
    }

    public void setY(double y) {
        pos.setLocation(pos.getX(), y);
    }

    public void levelUp() {
        maxHealth += character.getMaxHealthScale() * (playerLevel);
        maxMana += character.getMaxManaScale() * (playerLevel);
        abiPow += character.getAbiPowScale() * (playerLevel);
        phyPow += character.getPhyPowScale() * (playerLevel);
        armor += character.getArmorScale() * (playerLevel);
        magicResist += character.getMagicResistScale() * (playerLevel);
        avalableUpgrades++;
        playerLevel++;
        xpToNextLevel = playerLevel * 100;
        character.getPass().update(this);
    }

    public void recieveDamage(Ability a) {
        if (a.getDamageType() == Ability.DamageType.PHYSICAL) {
            currentHealth = a.getDamage() - this.armor;
        } else {
            currentHealth = a.getDamage() - this.magicResist;
        }
    }

    // index of cooldowns array
    public int getAbiLevel(int index) {
        return abilityLevels[index - 1];
    }

    public void useAbility(int index){
        if ( cooldowns[index] > 0){
            System.out.println("Cannot use that ability");
        } else {
            // abi.use();
        }
    }

    // index of abilitys array
    private void abilityUpgrade(int index) {
        if (avalableUpgrades < 0 && abilityLevels[index-1] < 5){
            abilityLevels[index-1]++;
            cooldowns[index-1] =  30 - (abilityLevels[index-1]*5);
            // abi.upgrade();
        }
    }
}
