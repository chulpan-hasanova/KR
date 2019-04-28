package pi.KR;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.JOptionPane;

public interface Calculation {
	
	static final int  k=365;
	static DecimalFormat df = new DecimalFormat("0.00");
	/**
	 * Функция расчёта суммы вклада на конец срока с учётом капитализации.
	 * @param p отображает значение суммы вклада
	 * @param i отображает значение процента вклада
	 * @param t отображает значение срока вклада в годах
	 * @return значение переменной sum после выполнения операций расчёта
	 * Метод возвращает значение строки равной сумме вклада + начисленный процент
	 */
	public static double kapitalOn(double p,double i, int t) {
		df.setRoundingMode(RoundingMode.FLOOR);
		double j, sum = 0,n = 0;
		n=t*12;
		j=(t*k)/n;
		sum=p*Math.pow((1+(i*j)/(100*k)),n);
		return sum;
	}
	
	/**
	 * Функция расчёта суммы процентов вклада на конец срока с учётом капитализации.
	 * @param p отображает значение суммы вклада
	 * @param i отображает значение процента вклада
	 * @param t отображает значение срока вклада в годах
	 * @return значение переменной sumP после выполнения операций расчёта
	 *  Метод возвращает значение строки равной начисленному проценту
	 */
	public static double kapitalOnSum(double p,double i, int t) {
		df.setRoundingMode(RoundingMode.FLOOR);
		double j,sumP=0,n = 0;
		n=t*12;
		j=(t*k)/n;
		sumP=p*Math.pow((1+(i*j)/(100*k)),n)-p;
		return sumP;
	}
	
	/**
	 * Функция расчёта суммы вклада на конец срока без учёта капитализации.
	 * @param p отображает значение суммы вклада
	 * @param i отображает значение процента вклада
	 * @param t отображает значение срока вклада в годах
	 * @return значение переменной sum после выполнения операций расчёта
	 * Метод возвращает значение строки равной сумме вклада + начисленный процент
	 */
	public static double kapitalOff(double p,double i, int t) {
		df.setRoundingMode(RoundingMode.FLOOR);
		double sum = 0;
		t=365*t;
		sum=p*(1+(i*t)/(100*k));
		return sum;
	}
	
	/**
	 * Функция расчёта суммы процентов вклада на конец срока без учёта капитализации.
	 * @param p отображает значение суммы вклада
	 * @param i отображает значение процента вклада
	 * @param t отображает значение срока вклада в годах
	 * @return значение переменной sumP после выполнения операций расчёта
	 *  Метод возвращает значение строки равной начисленному проценту
	 */
	public static double kapitalOffSum(double p,double i, int t) {
		df.setRoundingMode(RoundingMode.FLOOR);
		double sumP=0;
		t=365*t;
		sumP=(p*i*t)/(k*100);
		return sumP;
	}
	
	/**
	 * Функция создания табличного документа с подробной информацией о начислениях по вкладу
	 * @param p отображает значение суммы вклада
	 * @param i отображает значение процента вклада
	 * @param t отображает значение срока вклада в годах
	 * @throws FileNotFoundException
	 */
	public static void resulCSV(double p, double i, int t) {
		df.setRoundingMode(RoundingMode.FLOOR);
		 try (PrintWriter writer = new PrintWriter(new File("Отчёт.csv"))) {
			  double sumP=(p*(i/100))/(12*t);
		      StringBuilder sb = new StringBuilder();
		      sb.append("Month ;% sum ;sum");
		      sb.append('\n');
		    if (AdminForm.kapital.isSelected()) {
		    	for(int j=1;j<=t*12;j++) {
		    		double sum=p*(i/(100*t*12));
		    		p=p+sum;
		    		sb.append(j+";"+df.format(sum)+";"+df.format(p));
			    	  sb.append('\n');
		    	}
		    }
		    else {
		    	for(int j=1;j<=t*12;j++) {
		    	  sb.append(j+";"+df.format(sumP)+";"+df.format(p)+" rub");
		    	  sb.append('\n');
		    	  if (j==t*12) sb.append("Summ at the"+";"+"end of deposit"+";"+(df.format((p*i*t*365)/(k*100))));
		      }}
		      writer.write(sb.toString());

		    } catch (FileNotFoundException f) {
		      System.out.println(f.getMessage());
		    }
		 JOptionPane.showMessageDialog(null, "Успешно", "Отчёт создан", JOptionPane.PLAIN_MESSAGE);
	}
	
}
