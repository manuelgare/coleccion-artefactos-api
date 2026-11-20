/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dao.ArtefactoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.Artefacto;

@WebServlet("/artefactos")
public class ArtefactoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ArtefactoDAO artefactoDAO;
    private Gson gson = new Gson();

    public void init() {
        artefactoDAO = new ArtefactoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            List<Artefacto> artefactos = artefactoDAO.selectAllArtefactos();
            out.print(gson.toJson(artefactos));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error al obtener artefactos: " + e.getMessage() + "\"}");
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonPayload = sb.toString();

        try {
            Artefacto artefacto = gson.fromJson(jsonPayload, Artefacto.class);
            artefactoDAO.insertArtefacto(artefacto);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\":\"Artefacto añadido con éxito\", \"artefacto\": " + gson.toJson(artefacto) + "}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Error procesando datos: " + e.getMessage() + "\"}");
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }

    // EJERCICIO 5: editar un artefacto existente (verbo HTTP PUT)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Leemos el cuerpo JSON de la petición, igual que en doPost
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonPayload = sb.toString();

        try {
            // El JSON que llega ya incluye el id del artefacto a modificar
            Artefacto artefacto = gson.fromJson(jsonPayload, Artefacto.class);
            boolean actualizado = artefactoDAO.updateArtefacto(artefacto);
            if (actualizado) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"message\":\"Artefacto actualizado con éxito\", \"artefacto\": " + gson.toJson(artefacto) + "}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"No se encontró el artefacto a actualizar\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Error actualizando datos: " + e.getMessage() + "\"}");
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }

    // EJERCICIO 5: eliminar un artefacto (verbo HTTP DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // El id viene en la URL como parámetro: artefactos?id=5
            int id = Integer.parseInt(request.getParameter("id"));
            boolean eliminado = artefactoDAO.deleteArtefacto(id);
            if (eliminado) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"message\":\"Artefacto eliminado con éxito\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"No se encontró el artefacto con id " + id + "\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Error eliminando datos: " + e.getMessage() + "\"}");
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
}
