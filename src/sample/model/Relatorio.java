package sample.model;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import sample.model.JDBCs.JDBCFuncionarioDAO;
import sample.model.JDBCs.JDBCMaterialDAO;
import sample.model.JDBCs.JDBCRetiradaDAO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Relatorio {

    public void gerarRelatorio(LocalDate data){
        Document docu = new Document();
        int quantidade=0;
        double valor=0;

        try {
            PdfWriter.getInstance(docu,new FileOutputStream("./RelatorioDeRetiradas"+ LocalDate.now()+".pdf"));

            docu.open();
            docu.setPageSize(PageSize.A4);

//            Image image=  Image.getInstance("Imagens/user.png");
//            image.scaleToFit(100,100);
//            docu.add(image);

            Font f= new Font();
            f.setSize(20);
            f.setStyle(Font.BOLD);

            docu.add(new Paragraph( "\n                                    Relatório Retiradas\n\n\n",f));

            for (Retirada r:JDBCRetiradaDAO.getInstance().listar()) {
                quantidade++;
                valor+=r.getQuantidade();
            }

            docu.add(new Paragraph("Quantidade de Materiais Retirados no dia "+data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+": "+quantidade));

            docu.add(new Paragraph("\n"));

//            for(Retirada r:JDBCRetiradaDAO.getInstance().listar()){
//                System.out.println(r.getIdFuncionario().getNome());
//                docu.add(new Paragraph("Funcionario: " +r.getIdFuncionario().getNome()));
//                for (Material m:r.getMateriais()) {
//                    System.out.println(m);
//                    //docu.add(new Paragraph("Funcionario: " +r.getIdFuncionario().getNome()+ "Retirou: "+m.getNome()+" - "+r.getQuantidade()+" "+m.getUnidadeMedida().getNome()+" Data e hora da Retirada:"+r.getData()));
//                }
//            }
            docu.add(new Paragraph("\n"));
            docu.add(new Paragraph("\n Valor Total de itens retirados: "+valor));

            docu.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
