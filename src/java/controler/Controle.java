package controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Acesso;
import model.Departamento;
import model.EmpresaDao;

/**
 *
 * @author lucas.scampos4
 */
@WebServlet(name = "Controle", urlPatterns = {"/Controle"})
public class Controle extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        String flag, mensagem = "";
        flag = request.getParameter("flag");
        if(flag.equals("login")){
            String user, password;
            user = request.getParameter("usuario");
            password = request.getParameter("senha");
             
            EmpresaDao dao = new EmpresaDao();
            Acesso acesso = dao.validarLogin(user, password);
            
            if(acesso != null){
            String msg, cargo, nome;
            cargo = acesso.getFuncionario().getCargoFuncionario();
            nome = acesso.getFuncionario().getNomeFuncionario();
            msg = nome +", " + cargo;
            request.setAttribute("m", msg);
            
                if(cargo.equalsIgnoreCase("Dev")){
                    RequestDispatcher disp = request.getRequestDispatcher("./components/departamento/acessoGerente.jsp");
                    disp.forward(request, response);
                    
                }else if(cargo.equalsIgnoreCase("Patrão")){
                    RequestDispatcher disp = request.getRequestDispatcher("acessoPatrao.jsp");
                    disp.forward(request, response);
                }else{
                    RequestDispatcher disp = request.getRequestDispatcher("acessoOutro.jsp");
                    disp.forward(request, response);
                }
            
            }else{
                
                RequestDispatcher disp = request.getRequestDispatcher("mensagens.jsp");
                disp.forward(request, response);
                           
            }
            
        }
        else if (flag.equalsIgnoreCase("CadastroDepartamento")) {
            //agora eh necessario encapsular os dados no objeto departamento, para jogar na tabela do bd
            Departamento dep;
            dep = new Departamento();
            dep.setIdDepartamento(request.getParameter("idDepartamento"));
            dep.setNomeDepartamento(request.getParameter("nomeDepartamento"));
            dep.setFoneDepartamento(request.getParameter("telefoneDepartamento"));
            
            EmpresaDao dao = new EmpresaDao();
            int retorno = dao.salvarDepartamento(dep);
            
            switch (retorno) {
                case 1:
                    mensagem = "Departamento salvo com sucesso.";
                    break;
                case 2:
                    mensagem = "Departamento já cadastrado.";
                    break;
                case 3:
                    mensagem = "Erro: Entre em contato com o adminstrador.";
                    break;
                default:
                    break;
            }
            request.setAttribute("m", mensagem);
            RequestDispatcher disp = request.getRequestDispatcher("mensagens.jsp");
            disp.forward(request, response);
        }
        else if(flag.equalsIgnoreCase("listarDepartamento")){
            List<Departamento> departamentos = new EmpresaDao().listarDepartamento();
            request.setAttribute("listaDepartamentos", departamentos);
            RequestDispatcher disp = request.getRequestDispatcher("./components/departamento/Listar_departamento.jsp");
            disp.forward(request, response);
            
        }
        else if(flag.equalsIgnoreCase("consultarDepartamento")){
           
            String nomeDep = request.getParameter("nomeDepartamento");
            EmpresaDao dao = new EmpresaDao();
            
            List<Departamento> departamentos = dao.consultaDepartamento(nomeDep);
            
            request.setAttribute("listaDepartamentos", departamentos);
            RequestDispatcher disp = request.getRequestDispatcher("./components/departamento/Listar_departamento.jsp");
            disp.forward(request, response);
            
        }else if(flag.equalsIgnoreCase("ExcluirDepartamento")){
           String idDep = request.getParameter("idDep");
           EmpresaDao dao = new EmpresaDao();
           int resultado = dao.excluirDepartamento(idDep);
           if(resultado == 1){
                mensagem = "Departamento excluido com sucesso.";
                request.setAttribute("m", mensagem);
                RequestDispatcher disp = request.getRequestDispatcher("mensagens.jsp");
                disp.forward(request, response);
           }else if(resultado == 2){
                mensagem = "Esté departamento não existe.";
                request.setAttribute("m", mensagem);
                RequestDispatcher disp = request.getRequestDispatcher("mensagens.jsp");
                disp.forward(request, response);
           }else{
                mensagem = "Erro ao tentar excluir o departamento.";
                request.setAttribute("m", mensagem);
                RequestDispatcher disp = request.getRequestDispatcher("mensagens.jsp");
                disp.forward(request, response);
           }
           
        }
        else if(flag.equalsIgnoreCase("AlterarDepartamento")){
           String idDep = request.getParameter("idDep");
           String nomeDep = request.getParameter("nomeDep");
           String foneDep = request.getParameter("foneDep");
           
           EmpresaDao dao = new EmpresaDao();
           int resultado = dao.alterarDepartamento(idDep, nomeDep, foneDep);
           if(resultado == 1){
                mensagem = "Departamento alterado com sucesso.";
           }else{
                mensagem = "Erro ao tentar alterar o departamento.";
           }
            request.setAttribute("m", mensagem);
            RequestDispatcher disp = request.getRequestDispatcher("mensagens.jsp");
            disp.forward(request, response);
            
        }else if(flag.equalsIgnoreCase("buscarDepartamentos")){
            //busca o departamento por ID para alterar
            String idDep = request.getParameter("idDep");
            
            EmpresaDao dao = new EmpresaDao();
            Departamento dep = dao.buscarDepartamento(idDep);
            
            if (dep == null){
                
            request.setAttribute("m", "Departamento não encontrado");
            RequestDispatcher disp = request.getRequestDispatcher("mensagens.jsp");
            disp.forward(request, response);
            
            }else{
            
                request.setAttribute("dep", dep);
                RequestDispatcher disp = request.getRequestDispatcher("AlterarDepartamentoById.jsp");
                disp.forward(request, response);
            }
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
