package laba5;

import java.util.Date;
import java.util.Random;

public class Polynom {
    static final int MAX_A = 10;

    int polynomDegree = 0;
    int polynom[] = null;

    public Polynom() {
    	polynomDegree = 0;
        polynom = null;
    }

    public Polynom(int n) {
        assert (n >= 0);
        init(n, MAX_A);
    }

    public Polynom (int n, int max) {
        assert (n >= 0);
        init (n, max);
    }

    private void init (int n, int max_value) {
        polynom = new int[n + 1];
        polynomDegree = n;
        Random rand = new Random( (new Date()).getTime() );
        for (int i=0; i <= polynomDegree; i++) {
            polynom[i] = rand.nextInt( max_value)-max_value/2;
        }
    }

    public void Print() {
        if (polynomDegree > 1 ) {
            for (int i = polynomDegree; i > 1; i--) {
            	
                if (polynom[i] == 0) {
                    continue;
                }
                
                if (i == polynomDegree) {
                    System.out.print(polynom[i]+"x^"+i);
                    continue;
                }

                if (polynom[i] < 0) {
                    System.out.print(polynom[i] + "x^" + i);
                } 
                else {
                    System.out.print("+" + polynom[i] + "x^" + i);
                }
            }
            
            if (polynom[1] != 0) {
                if (polynom[1] < 0) {
                    System.out.print(polynom[1] + "x");
                } else {
                    System.out.print("+" + polynom[1] + "x");
                }
            }
            
            if (polynom[0] != 0) {
                if (polynom[0] < 0) {
                    System.out.print(polynom[0]);
                } else {
                    System.out.print("+" + polynom[0]);
                }
            }
        }
            else {
                if (polynomDegree == 1) {
                    if (polynom[1] != 0) {
                        System.out.print(polynom[1]+"x");
                    }
                    
                    if (polynom[0] != 0) {
                        if (polynom[0] < 0) {
                           System.out.print(polynom[0]);
                        }
                        else {
                            System.out.print("+"+polynom[0]);
                        }
                    }
                }
                else {
                    if (polynom[0] != 0) {
                        System.out.print(polynom[0]);
                    }
                }
            }

        System.out.println();
    }

    public Polynom Add ( Polynom second) {
        Polynom sum = new Polynom( Math.max(polynomDegree, second.polynomDegree) );
        
        for (int i = 0; i < Math.min(polynomDegree, second.polynomDegree)+1; i++ ) {
            sum.polynom[i] = polynom[i] + second.polynom[i];
        }
        
        if (polynomDegree > second.polynomDegree) {
            for (int i = second.polynomDegree + 1; i <= polynomDegree; i++) {
                sum.polynom[i] = this.polynom[i];
            }
        }
        
        if (second.polynomDegree > polynomDegree) {
            for (int i = polynomDegree + 1; i <= second.polynomDegree; i++) {
                sum.polynom[i] = second.polynom[i];
            }
        }
        
        return sum;
    }

    public Polynom Mult ( Polynom second ) {
        Polynom mult = new Polynom(polynomDegree + second.polynomDegree);
        
        for (int i = 0; i < mult.polynomDegree + 1; i++) {
            mult.polynom[i]=0;
        }
        
        for (int i = 0; i < polynomDegree + 1; i++) {
            for (int j = 0; j < second.polynomDegree + 1; j++) {
                mult.polynom[i+j] += second.polynom[j]*this.polynom[i];
            }
        }
        
        return mult;
    }
}

