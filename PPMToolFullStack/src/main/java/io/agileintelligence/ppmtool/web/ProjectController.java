package io.agileintelligence.ppmtool.web;


import io.agileintelligence.ppmtool.Services.ProjectService;
import io.agileintelligence.ppmtool.domain.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {


    private final MapValidationErrorService mapValidationErrorService;
    private final ProjectService projectService;

    public ProjectController(MapValidationErrorService mapValidationErrorService, ProjectService projectService) {
        this.mapValidationErrorService = mapValidationErrorService;
        this.projectService = projectService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationErrorService(result);

        if(errorMap != null) return errorMap;

        Project project1 = projectService.saveOrUpdateProject(project);

        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectId(@PathVariable String projectId){
        Project project =projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleProject(@PathVariable String projectId){
        projectService.deleteProjectById(projectId.toUpperCase());
        return new ResponseEntity<String>("Project with ID: '" + projectId +"'" + " was deleted", HttpStatus.OK);
    }

}