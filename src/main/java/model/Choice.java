package model;

public class Choice {
    private int idChoice;
    private String text;
    private boolean locked;
    private boolean onlyChoice;
    private boolean condShouldPass;
    private Paragraph paragOrigin;
    private Paragraph paragDest;
    private Paragraph paragCond;

    public Choice(int idChoice, String text, boolean locked, boolean onlyChoice, boolean condShouldPass, Paragraph paragOrigin, Paragraph paragDest, Paragraph paragCond) {
        this.idChoice = idChoice;
        this.text = text;
        this.locked = locked;
        this.onlyChoice = onlyChoice;
        this.condShouldPass = condShouldPass;
        this.paragOrigin = paragOrigin;
        this.paragDest = paragDest;
        this.paragCond = paragCond;
    }


    public int getIdChoice() {
        return idChoice;
    }

    public String getText() {
        return text;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isOnlyChoice() {
        return onlyChoice;
    }

    public boolean isCondShouldPass() {
        return condShouldPass;
    }

    public Paragraph getParagOrigin() {
        return paragOrigin;
    }

    public Paragraph getParagDest() {
        return paragDest;
    }

    public Paragraph getParagCond() {
        return paragCond;
    }

    public void setParagOrigin(Paragraph paragOrigin) {
        this.paragOrigin = paragOrigin;
    }

    public void setParagDest(Paragraph paragDest) {
        this.paragDest = paragDest;
    }

    public void setParagCond(Paragraph paragCond) {
        this.paragCond = paragCond;
    }


    public String getLabelText() {
        if (text.length() > 10) {
            return text.substring(0, 6) + "...";
        }
        return text;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.idChoice;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Choice other = (Choice) obj;
        if (this.idChoice != other.idChoice) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Choice{" + "idChoice=" + idChoice + ", text=" + text + ", locked=" + locked + ", onlyChoice=" + onlyChoice + ", condShouldPass=" + condShouldPass + ", paragOrigin=" + paragOrigin + ", paragDest=" + paragDest + ", paragCond=" + paragCond + '}';
    }


}
