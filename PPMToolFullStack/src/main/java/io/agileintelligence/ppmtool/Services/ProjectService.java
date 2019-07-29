package io.agileintelligence.ppmtool.Services;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.exceptions.ProjectIdException;
import io.agileintelligence.ppmtool.repositories.BackLogRepository;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BackLogRepository backLogRepository;



    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId() == null){
                Backlog backlog =new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backLogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);

        }catch (Exception e){
            throw new ProjectIdException("Project ID '" +project.getProjectIdentifier().toUpperCase() + "' already exists");
        }

    }

    public Project findProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project==null){
            throw  new ProjectIdException("Project ID '" + projectId + "' does not exists");
        }
        return project;

    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectById(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId);
        if (project == null){
            throw  new ProjectIdException("Cannot delete project qith ID '" + projectId +"'. This project does not exist");
        }

        projectRepository.deleteById(project.getId());
    }
}
