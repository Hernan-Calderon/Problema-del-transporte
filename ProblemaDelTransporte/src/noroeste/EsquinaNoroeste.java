package noroeste;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * Programa que resuelve el problema del transporte
 * a través del algoritmo de esquina noroeste.
 * 
 * @author Hernán Alonso Calderón
 * 
 */

public class EsquinaNoroeste extends JFrame {

	private JPanel contentPane;
	private JTextField textFilas;
	private JTextField textColumnas;
	private JTable table1;
	private JTable table2;
	private JTextArea resultado;

	/**
	 * Ejecuta la aplicación.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EsquinaNoroeste frame = new EsquinaNoroeste();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea el frame.
	 */
	public EsquinaNoroeste() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnTablaCostos = new JButton("Ingresar Costos");
		btnTablaCostos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				/*
				 * Se genera una tabla con el número de filas y columnas
				 * correspondiente con el número de ofertas y demandas
				 */
				DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
				int fil = Integer.parseInt(textFilas.getText());
				int col = Integer.parseInt(textColumnas.getText());
				modelo.setRowCount(fil+1);
				modelo.setColumnCount(col+1);
				
			}
		});
		btnTablaCostos.setBounds(369, 11, 139, 23);
		contentPane.add(btnTablaCostos);
		
		JLabel lblFilas = new JLabel("Oferta:");
		lblFilas.setBounds(32, 15, 46, 14);
		contentPane.add(lblFilas);
		
		textFilas = new JTextField();
		textFilas.setBounds(77, 12, 86, 20);
		contentPane.add(textFilas);
		textFilas.setColumns(10);
		
		JLabel lblColumnas = new JLabel("Demanda:");
		lblColumnas.setBounds(194, 15, 65, 14);
		contentPane.add(lblColumnas);
		
		textColumnas = new JTextField();
		textColumnas.setBounds(255, 12, 86, 20);
		contentPane.add(textColumnas);
		textColumnas.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 59, 476, 250);
		contentPane.add(scrollPane);
		
		table1 = new JTable();
		scrollPane.setViewportView(table1);
		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * Se genera la tabla de las unidades
				 */
				DefaultTableModel modelo = (DefaultTableModel) table2.getModel();
				int fil = Integer.parseInt(textFilas.getText());
				int col = Integer.parseInt(textColumnas.getText());
				modelo.setRowCount(fil);
				modelo.setColumnCount(col);
				/*
				 * Se crean los vectores y matrices que se utilizan para realizar los 
				 * cálculos
				 */
				int [][] unidades = new int[fil][col];        
		        Float [][] costos = new Float[fil][col]; 
		        int [] oferta = new int[fil];
		        int [] demanda = new int[col];
		        /*
		         * Se asignan los datos ingresados, correspondientes a los costos, 
		         * a la matriz de costos.
		         */
		        for(int i = 0; i < fil;i++) {
		        	for(int j = 0; j < col;j++) {
		        		
		        		costos[i][j]=Float.parseFloat((String) table1.getValueAt(i, j)); 
		            }
		        }
		        /*
		         * Se asignan los datos ingresados, correspondientes a las ofertas,
		         * al vector de ofertas.
		         */
		        for(int i =0;i<fil;i++) {		        	
		        	oferta[i] = Integer.parseInt((String) table1.getValueAt(i, col));
		        }
		        /*
		         * Se asignan los datos ingresados, correspondientes a las demandas,
		         * al vector de demandas.
		         */
		        for(int i =0;i<col;i++) {		        	
		        	demanda[i] = Integer.parseInt((String) table1.getValueAt(fil, i));
		        }
		        
		        int i = 0;
		        int j = 0;
		        /*
		         * Algoritmo esquina noroeste.
		         */
		        while(i< fil && j < col) {        	
		        	if(oferta[i] == demanda[j]) {        		
		        		unidades[i][j]= oferta[i];
		        		oferta[i] = 0;
		        		demanda[j] = 0; 
		        		for(int k = i+1; k<fil;k++) {
		        			unidades[k][j] = 0;
		        		}
		        		for(int k = j+1; k<col;k++) {
		        			unidades[i][k] = 0;
		        		}       		
		        		i+=1;
		        		j+=1;        		
		        	}
		        	else if(oferta[i] < demanda[j]) {        		
		        		unidades[i][j]= oferta[i];
		        		demanda[j] = demanda[j]-oferta[i];
		        		oferta[i] = 0;
		        		
		        		for(int k = j+1; k<col;k++) {
		        			unidades[i][k] = 0;
		        		}        		
		        		i=i+1;      		
		        	}
		        	else{        		
		        		unidades[i][j]=demanda[j];
		        		oferta[i] =oferta[i] - demanda[j];
		        		demanda[j] = 0;
		        		
		        		for(int k = i+1; k<fil;k++) {
		        			unidades[k][j] = 0;
		        		}        		
		        		j= j+1;        		
		        	}
		        }
		        /*
		         * Se muestran los datos de las unidades en una tabla
		         */
		        for(int k = 0; k < fil;k++) {
		        	for(int l = 0; l < col;l++) {		        		
		        		table2.setValueAt(unidades[k][l],k, l); 
		            }
		        }
		        /*
		         * Se calcula el costo total asociado al modelo.
		         */
		        float costoTotal = 0;
		        for(int k = 0; k < fil;k++) {
		        	for(int l = 0; l < col;l++) {
		        		costoTotal += unidades[k][l] * costos[k][l];
		        	}        	
		        }		        
		        resultado.setText(" "+costoTotal);		        
				
			}
		});
		btnCalcular.setBounds(419, 330, 89, 23);
		contentPane.add(btnCalcular);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(32, 365, 476, 250);
		contentPane.add(scrollPane_1);
		
		table2 = new JTable();
		scrollPane_1.setViewportView(table2);
		
		JLabel lblCostoTotal = new JLabel("Costo Total Asociado:");
		lblCostoTotal.setBounds(32, 626, 131, 14);
		contentPane.add(lblCostoTotal);
		
		resultado = new JTextArea();
		resultado.setBounds(160, 626, 113, 14);		
		contentPane.add(resultado);
	}
}
