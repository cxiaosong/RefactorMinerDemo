package baseMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import concreteMiner.extractMethod;
import config.Configuration;
import edu.lu.uni.serval.git.travel.GitRepository;
import edu.lu.uni.serval.utils.FileHelper;

public class test004 {
    private static Logger log = Logger.getLogger(test004.class.getClass());

	public static void main(String[] args) throws Exception {

		//初始化
		FileHelper.deleteDirectory(Configuration.REFACTORING_BEFOR_METHOD);
		FileHelper.deleteDirectory(Configuration.REFACTORING_AFTER_METHOD);
		FileHelper.createDirectory(Configuration.REFACTORING_BEFOR_METHOD);
		FileHelper.createDirectory(Configuration.REFACTORING_AFTER_METHOD);
		
		List<String> projectList = readList(Configuration.PROCESS_PROJECT_LIST);
		for (String project : projectList) {
			String repository = Configuration.JAVA_REPOS_PATH+project;
			log.warn("开始挖掘："+project);
			traverseGitRepos(repository);
			log.warn("挖掘完成^_^");

		}
	
		

	}
	public static void main2(String[] args) throws Exception {
		String repository = args[0];
		log.warn("开始挖掘：");
		traverseGitRepos(repository);
		log.warn("挖掘完成^_^");

	}
	private static void traverseGitRepos(String repository) throws Exception {
		// 获取commit的id和git地址
		GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
		GitService gitService = new GitServiceImpl();
		Repository repo = gitService.openRepository(repository);
		final String repositoryId = repository + "/.git";
		// 获取重构的目标方法名字
		try {
			miner.detectAll(repo, repo.getBranch(), new extractMethod(repositoryId));
		} catch (Exception e) {
			// TODO: handle exception
			log.warn("系统错误"+e.getMessage());
		}
	}
	public static List<String> readList(String fileName) {
		List<String> list = new ArrayList<String>();
		String content = FileHelper.readFile(fileName);
		BufferedReader reader = new BufferedReader(new StringReader(content));
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	private String getParentCommidId(String commitId, String repositoryId) {
		// TODO Auto-generated method stub
		String parentCommitId = null;
		GitRepository gitRepo = new GitRepository(repositoryId, "", "");

		List<RevCommit> commits;
		try {
			commits = gitRepo.getAllCommits(false);
			for (int i = 0; i < commits.size(); i++) {
				if (commits.get(i).toString().split(" ")[1].equals(commitId)) {
					parentCommitId = commits.get(i + 1).toString().split(" ")[1];
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parentCommitId;
	}

}
