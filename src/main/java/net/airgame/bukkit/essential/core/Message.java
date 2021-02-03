package net.airgame.bukkit.essential.core;

public enum Message {
    prefix("§a[五彩方块] "),
    playerBedEnter("玩家 %player% 睡觉了."),
    allPlayerSleeping("所有玩家都入睡了~"),
    notAllPlayerSleeping("当前未入睡的玩家: %not_sleep_players%");
    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return prefix.message + message;
    }
}
