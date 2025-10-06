
public class Bruch {
    private int zaehler;
    private int nenner;

    public Bruch(int zaehler, int nenner) {
        if (nenner == 0) throw new IllegalArgumentException("Nenner darf nicht 0 sein!");
        this.zaehler = zaehler;
        this.nenner = nenner;
        kuerzen();
    }

    public Bruch addiere(Bruch anderer) {
        int z = this.zaehler * anderer.nenner + anderer.zaehler * this.nenner;
        int n = this.nenner * anderer.nenner;
        return new Bruch(z, n);
    }

    public Bruch subtrahiere(Bruch anderer) {
        int z = this.zaehler * anderer.nenner - anderer.zaehler * this.nenner;
        int n = this.nenner * anderer.nenner;
        return new Bruch(z, n);
    }

    public Bruch multipliziere(Bruch anderer) {
        int z = this.zaehler * anderer.zaehler;
        int n = this.nenner * anderer.nenner;
        return new Bruch(z, n);
    }

    public Bruch dividiere(Bruch anderer) {
        if (anderer.zaehler == 0) throw new IllegalArgumentException("Division durch 0!");
        int z = this.zaehler * anderer.nenner;
        int n = this.nenner * anderer.zaehler;
        return new Bruch(z, n);
    }

    private void kuerzen() {
        int g = ggT(Math.abs(zaehler), Math.abs(nenner));
        zaehler /= g;
        nenner /= g;
        if (nenner < 0) { nenner = -nenner; zaehler = -zaehler; }
    }

    private int ggT(int a, int b) {
        while (b != 0) { int t = b; b = a % b; a = t; }
        return a;
    }


    public int getZaehler() { return zaehler; }
    public int getNenner()  { return nenner; }

    @Override public String toString() { return zaehler + "/" + nenner; }
}
