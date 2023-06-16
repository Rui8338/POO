import java.io.Serializable;

public enum Tone implements Serializable {
    NEUTRAL, WARM, COLD;

    public static Tone getTone(String tone)
    {
        Tone t = null;
        String tone2 = tone.trim().toLowerCase();
        if (tone2.equals("neutral"))
        {
            t = Tone.NEUTRAL;
        }
        else if (tone2.equals("warm"))
        {
            t = Tone.WARM;
        }
        else if (tone2.equals("cold"))
        {
            t = Tone.COLD;
        }
        return t;
    }

    @Override
    public String toString() {
        String res;
        switch (this) {
            case COLD:
                res = "Cold";
                break;
            case WARM:
                res = "Warm";
                break;
            case NEUTRAL:
                res = "Neutral";
                break;
            default:
                res = "Undefined";
                break;
        }
        return res;
    }

}
