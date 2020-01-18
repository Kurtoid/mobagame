package mobagame.core.game;

import mobagame.core.game.characters.Jack;

import java.awt.geom.Point2D;

public class InGamePlayer extends GameObject {

    private int playerID;
    private Character character = new Jack();
    private boolean dead;

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setDeathTime() {
        deathTime = this.getLevel() * 5;
    }

    public void kill() {
        setDeathTime();
        setRespawnTime();
        setDead(true);
    }

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
    public Game game;
    String username;
    long lastAttackTime;
    long respawnTime;

    // 0 = Q, 1 = W, 2 = E, 3 = R
    public int[] abilityLevels = { 0, 0, 0, 0 };
    public int[] cooldowns = { 0, 0, 0, 0 };

    public Item[] inventory = { (GameItems.empty), (GameItems.empty), (GameItems.empty), (GameItems.empty),
            (GameItems.empty), (GameItems.empty), (GameItems.empty), (GameItems.empty) };
    private int deathTime;

    public InGamePlayer(int playerID, String playerUsername, Team gameTeam) {
        super();
        this.playerID = playerLevel;
        this.username = playerUsername;
        this.team = gameTeam;
    }

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
        this.currentHealth = character.getBaseMaxHealth();
        this.maxHealth = character.getBaseMaxHealth();
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
        return "phyPow = " + phyPow + ", abiPow = " + abiPow + ", maxHealth = " + maxHealth + ", maxMana = " + maxMana
                + ", speed = " + speed + ", armor = " + armor + ", magicResist =" + magicResist + ", currentHealth = "
                + currentHealth + ", currentMana = " + currentMana + ", goldAmount = " + goldAmount + ", xp = " + xp
                + ", levelXp = " + levelXp + ", xpToNextLevel = " + xpToNextLevel + ", playerLevel = " + playerLevel
                + ", position = " + pos;
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

    public void useAbility(int index) {
        if (cooldowns[index] > 0 || abilityLevels[index] == 0) {
            System.out.println("Cannot use that ability");
        } else {
            // abi.use();
        }
    }

    // index of abilitys array
    private void abilityUpgrade(int index) {
        if (avalableUpgrades < 0 && abilityLevels[index] < 5) {
            abilityLevels[index]++;
            cooldowns[index] = 30 - (abilityLevels[index] * 5);
            // abi.upgrade();
        }
    }

    public boolean canAttack() {
        return System.currentTimeMillis() > (lastAttackTime + character.getAutoAttackCooldown());
    }

    /**
     * gets a valid target for the player TODO: make range based off of character
     * 
     * @return
     */
    public GameObject getAttackTarget(double range) {
        GameObject target = null;
        double minDistance = Double.MAX_VALUE;

        for (InGamePlayer p : game.players) {
            if (GameTeams.getOppositeTeam(team) == p.team) {
                if (p.pos.distance(pos) < minDistance && p.pos.distance(pos) <= range) {
                    minDistance = p.pos.distance(pos);
                    target = p;
                }
            }
        }
        for (Tower p : game.map.towers) {
            if (GameTeams.getOppositeTeam(team) == p.team && p.pos.distance(pos) <= range) {
                if (p.pos.distance(pos) < minDistance) {
                    minDistance = p.pos.distance(pos);
                    target = p;
                }
            }
        }

        return target;
    }

    public int getDeathTime() {
        return this.deathTime;
    }

    public void setRespawnTime() {
        respawnTime = System.currentTimeMillis() + (deathTime * 1000);
    }

    public long getRespawnTime() {
        return this.respawnTime;
    }

    public SeekingProjectile attackTarget(GameObject target, Game g) {
        lastAttackTime = System.currentTimeMillis();
        SeekingProjectile p = new SeekingProjectile(g.map);
        p.target = new Point2D.Double(target.pos.getX(), target.pos.getY());
        p.firedBy = this;
        p.firedFrom = new Point2D.Double(pos.getX(), pos.getY());
        p.team = this.team;
        p.pos = new Point2D.Double(pos.getX(), pos.getY());
        p.mover.setTarget(p.target.getX(), p.target.getY());
        p.targetObject = target;
        return p;

    }
}
