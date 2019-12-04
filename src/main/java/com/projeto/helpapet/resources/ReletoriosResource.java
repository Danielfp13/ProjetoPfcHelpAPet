package com.projeto.helpapet.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.helpapet.model.repositories.InstituicaoRepository;
import com.projeto.helpapet.model.services.InstituicaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Api(tags = "Relatorios Endpoint")
@RestController
@RequestMapping(value = "/relatorio")
public class ReletoriosResource {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private InstituicaoService service;

	@Autowired
	public InstituicaoRepository instituicaoRepository;

	@RequestMapping(value = "/termo/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('INSTITUICAO')")
	@ApiOperation(value = "Termo de adoção")
	public void termoDeAdocao(@RequestParam Map<String, Object> parametros, HttpServletResponse response,
			@PathVariable Integer id) throws JRException, SQLException, IOException {

		parametros = parametros == null ? parametros = new HashMap<>() : parametros;
		parametros.put("id", id);
		// Pega o arquivo .jasper localizado em resources
		InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/Blank_A4.jasper");

		// Cria o objeto JaperReport com o Stream do arquivo jasper
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		// Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no
		// caso uma conexão ao banco de dados
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

		// Configura a respota para o tipo PDF
		response.setContentType("application/pdf");
		// Define que o arquivo pode ser visualizado no navegador e também nome final do
		// arquivo
		// para fazer download do relatório troque 'inline' por 'attachment'
		response.setHeader("Content-Disposition", "inline; filename=termo.pdf");

		// Faz a exportação do relatório para o HttpServletResponse
		final OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}
}
