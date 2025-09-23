package entity;

public class AbonnementAvecEngagement extends Abonnement{
    private int dureeEngagement_mois;

    public AbonnementAvecEngagement(int dureeEngagement_mois) {
        this.dureeEngagement_mois = dureeEngagement_mois;
    }

    public int getDureeEngagement_mois() {
        return dureeEngagement_mois;
    }

    public void setDureeEngagement_mois(int dureeEngagement_mois) {
        this.dureeEngagement_mois = dureeEngagement_mois;
    }

    @Override
    public String toString() {
        return "AbonnementAvecEngagement{" +
                "dureeEngagement_mois=" + dureeEngagement_mois +
                '}';
    }
}
