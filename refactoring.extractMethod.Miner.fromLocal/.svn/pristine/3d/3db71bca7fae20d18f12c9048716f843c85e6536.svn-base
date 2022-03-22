package concreteMiner;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;

import baseMain.test004;
import config.Configuration;
import config.Counter;
import edu.lu.uni.serval.git.travel.CommitDiffEntry;
import edu.lu.uni.serval.git.travel.GitRepository;
import edu.lu.uni.serval.utils.FileHelper;
import gr.uom.java.xmi.diff.ExtractOperationRefactoring;

public class extractMethod extends RefactoringHandler{
    private static Logger log = Logger.getLogger(extractMethod.class.getClass());

	String repositoryId ;
	String refactoringBeforDir;
	String refactoringAfterDir;
	public extractMethod() {
		// TODO Auto-generated constructor stub
		super();
	}
	public extractMethod(String repositoryId) {
		// TODO Auto-generated constructor stub
		this.repositoryId=repositoryId;
		
	}
	@Override
	public void handle(String commitId, List<Refactoring> refactorings) {
		// TODO Auto-generated method stub

		GitRepository gitRepo = null;
		try {
			int refactoringTimes=0;
			List<RevCommit> commits = null;
			 
			for (Refactoring ref : refactorings) {
				if (ref instanceof ExtractOperationRefactoring) {
					log.warn("提取项目所有提交内容");
					gitRepo = new GitRepository(repositoryId, "", "");
					gitRepo.open();
					commits = gitRepo.getAllCommits(false);
					break;
				}
			}
			for (Refactoring ref : refactorings) {
				if (ref instanceof ExtractOperationRefactoring) {
					
					ExtractOperationRefactoring refactoring = (ExtractOperationRefactoring) ref;
					String  abstractClassPath=refactoring.toString().split("class ")[1];
					
					log.warn("提取类内容");
					String oldClassContent = "";

					
					
					oldClassContent = getOldClass(gitRepo,commits,abstractClassPath.replace(".", "/"),commitId, repositoryId);
					String methodNameOld =   refactoring.getBodyMapper().getCallSiteOperation().getName();
					String newClassContent = "";
					newClassContent = getNewClass(gitRepo,commits,refactoring.toString().split("class ")[1].replace(".", "/"),commitId, repositoryId);
					String methodNameNew =   refactoring.getBodyMapper().getOperation2().getName();
					
					
					
					String fileName=commitId+abstractClassPath+"-"+refactoringTimes+".txt";
					if (newClassContent!=null&&newClassContent.length()>0
							&&oldClassContent!=null&&oldClassContent.length()>0) {
						log.warn("提取重构方法对");
						String methodBodyOld = getMessageBodyUseMessageName(methodNameOld, oldClassContent);
						String methodBodyNew = getMessageBodyUseMessageName(methodNameNew, newClassContent);
						FileHelper.createFile(new File(Configuration.REFACTORING_BEFOR_METHOD+fileName), methodBodyOld);
						FileHelper.createFile(new File(Configuration.REFACTORING_AFTER_METHOD+fileName), methodBodyNew);
						log.warn("共挖掘："+Counter.printTimes()+"对方法");
						refactoringTimes++;
					}else {
						log.warn("提取重构方法为空：commitID"+"-"+commitId+"-"+"repositoryID:"+"-"+repositoryId);
						
					}
					
				
					


	 
				}
			}
			
			
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			if (gitRepo!=null) {
				gitRepo.close();
				
			}
		}

		
	
	}
	public  String getMessageBodyUseMessageName(String methodName, String parentRawFile) {
		String methodBody = "";
		ASTParser parser = ASTParser.newParser(AST.JLS3); // Java 5.0 and up
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(parentRawFile.toCharArray());
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		// 根据方法名获取方法体的内容
		CompilationUnit unit = (CompilationUnit) parser.createAST(null);
		List<?> delcsTmp = ((TypeDeclaration) unit.types().get(0)).bodyDeclarations();
		for (Iterator<?> iterator = delcsTmp.iterator(); iterator.hasNext();) {

			BodyDeclaration decl = (BodyDeclaration) iterator.next();
			if (decl instanceof MethodDeclaration) {
				if (((MethodDeclaration) decl).getName().getIdentifier().equals(methodName)) {
					methodBody = ((MethodDeclaration) decl).toString();
					break;
				}
			}

		}
		return methodBody;
	}
	private static String getOldClass(GitRepository gitRepo,List<RevCommit> commits,String classPath,String commitId, String repositoryId) {
		
		try {
			
			List<RevCommit> commitOne=new ArrayList<RevCommit>();
			for (int i = 0; i < commits.size(); i++) {
				RevCommit revCommit=commits.get(i);
				if (commitId.equals(revCommit.toString().split(" ")[1])) {
					commitOne.add(revCommit);
					for (RevCommit parentRevCommit: revCommit.getParents()) {
						commitOne.add(parentRevCommit);
					}

				} 
			}
			
			List<CommitDiffEntry> commitDiffentries = gitRepo.getCommitDiffEntries(commitOne);
			List<String> pathList=new ArrayList<String>();
			for (CommitDiffEntry commitDiffEntry : commitDiffentries) {
				if (commitDiffEntry.getDiffentry().getNewPath().indexOf(classPath)>0) {
					pathList.add(commitDiffEntry.getDiffentry().getNewPath());
				}
			}
			
			
			for (String string : pathList) {
				String content=gitRepo.getFileContent(commitOne.get(1),string);
				if (content!=null) {
					return content;
				}
				
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		return null;
	}
	
	private static String getNewClass(GitRepository gitRepo,List<RevCommit> commits,String classPath,String commitId, String repositoryId) {
		try {
			List<RevCommit> commitOne=new ArrayList<RevCommit>();
			for (int i = 0; i < commits.size(); i++) {
				RevCommit revCommit=commits.get(i);
				if (commitId.equals(revCommit.toString().split(" ")[1])) {
					commitOne.add(revCommit); 

				} 
			} 
			List<CommitDiffEntry> commitDiffentries = gitRepo.getCommitDiffEntries(commitOne);
			for (int i = 0; i < commitDiffentries.size(); i++) {
				if (commitDiffentries.get(i).getCommit().toString().split(" ")[1].equals(commitId)) {
					for (int j = i ; j < commitDiffentries.size(); j++) {
						if (commitDiffentries.get(j).getDiffentry().getNewPath().indexOf(classPath)<0) {
							continue;
						}else{
							CommitDiffEntry newCommitDiffEntry = commitDiffentries.get(j);
							String newClass = gitRepo.getFileContent(newCommitDiffEntry.getCommit(),newCommitDiffEntry.getDiffentry().getNewPath());
							return newClass;
						}
					}
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		return null;
	}
}
