package ca.yorku.eecs.mcalc;

public class MortgageModel
{
    private double principle;
    private int amortization;
    private double interest;

    public MortgageModel(String p, String a, String i){
        this.principle = Double.parseDouble(p);
        this.amortization = (Integer.parseInt(a))*12;
        this.interest = (Double.parseDouble(i)/(100*12));

    }

    public String computePayment(){
        double r = this.interest;
        double n = this.amortization;
        double monthlyPayment = ((r*this.principle)/(1-(Math.pow(1+r,-n))));
        String result = String.format("$%,.2f", monthlyPayment);
        return result;
    }

    public static void main(String args[]){

        MortgageModel myModel = new MortgageModel("700000", "25", "2.75");
        System.out.println(myModel.computePayment());

        myModel = new MortgageModel("300000", "20", "4.50");
        System.out.println(myModel.computePayment());

    }
}
