package com.one.mongoreactivehumanresources.bussiness_services;

import com.one.mongoreactivehumanresources.documents.Employee;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PdfService {

    public static final String PDF_FILE_EXT = ".pdf";

    private String logo = "";
    private String name = "";
    private String nif = "";
    private String phone = "";
    private String address = "";
    private String email = "";
    private String web = "";

    private FileService fileService;

    public PdfService(FileService fileService) {
        this.fileService = fileService;
    }

    public Mono<byte[]> generateEmployee(Mono<Employee> employeeReact) {
        return employeeReact.map(employee -> {
            final String path = "/pdfs/employees/employee-" + employee.getId();
            PdfBuilder pdf = new PdfBuilder(path);


            return null;
        });
    }
}
