package breakout;

public class PoweredBrick extends NormalBrick {

    private PowerUp myPowerUp;

    public PoweredBrick(double X, double Y, double rectWidth, double rectHeight, int hits, String power) {
        super(X, Y, rectWidth, rectHeight, hits);
        myPowerUp = new PowerUp(power);
    }

    @Override
    public void handleHit() {
        super.handleHit();
        if (myHitsLeft == 0)  {
            myPowerUp.dropPowerUp(this);
        }
    }

    public PowerUp getPowerUp() {
        return myPowerUp;
    }
}
