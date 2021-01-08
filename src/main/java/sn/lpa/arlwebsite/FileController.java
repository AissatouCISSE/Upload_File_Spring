package sn.lpa.arlwebsite;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import antlr.StringUtils;

@Controller
public class FileController {

	@Autowired
	private DocumentRepository repo;
	
	@GetMapping("/")
	public ModelAndView liste() {
		List<Document> listDocs = repo.findAll();
		return new ModelAndView( "home","listDocs",  listDocs);
	}
	
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("document") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
			String fileName =org.springframework.util.StringUtils.cleanPath( multipartFile.getOriginalFilename());
			Document document = new Document();
			document.setName(fileName);
			document.setContent(multipartFile.getBytes());
			document.setSize(multipartFile.getSize());
			document.setUploadTime(new Date());
			repo.save(document);
			ra.addFlashAttribute("message","le fichier a ete bien stocke dans la base de donnee.");
			return "redirect:/";
	}
}
