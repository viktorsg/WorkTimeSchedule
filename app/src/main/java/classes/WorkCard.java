package classes;

public class WorkCard {

    private int cardId;
    private String startTime;
    private String endTime;
    private int cardTaskId;
    private int cardEmployeeId;
    private String cardDescription;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCardTaskId() {
        return cardTaskId;
    }

    public void setCardTaskId(int cardTaskId) {
        this.cardTaskId = cardTaskId;
    }

    public int getCardEmployeeId() {
        return cardEmployeeId;
    }

    public void setCardEmployeeId(int cardEmployeeId) {
        this.cardEmployeeId = cardEmployeeId;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }
}
