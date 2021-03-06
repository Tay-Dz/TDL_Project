package com.qa.TDL_Project.selenium;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.tomcat.jni.Time;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PriorityPageTest {
	
	public static WebDriver driver;

	@BeforeClass
	public static void initialise() {
		System.setProperty("webdriver.edge.driver","C:\\Users\\taydz\\Desktop\\workspace-spring-tool-suite-4-4.8.0.RELEASE\\TDL_Project\\src\\test\\resources\\msedgedriver.exe");
		driver = new EdgeDriver();
	}
	
	@Test
	public void CRUDTest() throws Exception{
		driver.get("http://127.0.0.1:5500/src/main/resources/static/html/TaskList.html?order=priority");
		
		driver.findElement(By.id("addTLButton")).click();
		WebDriverWait wait = new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("exampleModal"))));
		driver.findElement(By.id("TaskListName")).sendKeys("TaskList3");
		driver.findElement(By.id("TaskListPriority")).sendKeys("High");
		driver.findElement(By.id("AddTLSubmit")).click();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("exampleModal"))));
		String TLname = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[2]/h2")).getText();
		assertThat(TLname).isEqualTo("TaskList3");
		String TLPriority = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table")).getAttribute("class");
		assertThat(TLPriority).isEqualTo("table High");
		
		driver.findElement(By.id("EditTLButtonTaskList3")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("EditTLModal"))));
		String EditTLPlaceholder = driver.findElement(By.id("EditTaskListName")).getAttribute("value");
		assertThat(EditTLPlaceholder).isEqualTo("TaskList3");
		driver.findElement(By.id("EditTaskListName")).sendKeys("Edit");
		driver.findElement(By.id("EditTaskListPriority")).sendKeys("Medium");
		driver.findElement(By.id("EditTLSubmit")).click();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("EditTLModal"))));
		String EditTLname = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[2]/h2")).getText();
		assertThat(EditTLname).isEqualTo("TaskList3Edit");
		String EditTLPriority = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table")).getAttribute("class");
		assertThat(EditTLPriority).isEqualTo("table Medium");
		
		driver.findElement(By.id("AddTaskButtonTaskList3Edit")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("AddTaskModal"))));
		String AddTaskTitle= driver.findElement(By.id("addTitle")).getText();
		assertThat(AddTaskTitle).isEqualTo("Add Task to TaskList3Edit");
		driver.findElement(By.id("AddTaskName")).sendKeys("Task1");
		driver.findElement(By.id("AddTaskPriority")).sendKeys("High");
		driver.findElement(By.id("AddTaskSubmit")).click();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("AddTaskModal"))));
		String AddTaskname = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table/thead/tr[2]/td[1]")).getText();
		assertThat(AddTaskname).isEqualTo("Task1");
		String AddTaskPriority = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table/thead/tr[2]/td[2]")).getText();
		assertThat(AddTaskPriority).isEqualTo("High");
		
		driver.findElement(By.id("EditTaskButtonTaskList3EditTask1")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("EditTaskModal"))));
		String EditTaskPlaceholder = driver.findElement(By.id("EditTaskName")).getAttribute("value");
		assertThat(EditTaskPlaceholder).isEqualTo("Task1");
		driver.findElement(By.id("EditTaskName")).sendKeys("Edit");
		driver.findElement(By.id("EditTaskPriority")).sendKeys("Medium");
		driver.findElement(By.id("EditTaskSubmit")).click();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("EditTaskModal"))));
		String EditTaskname = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table/thead/tr[2]/td[1]")).getText();
		assertThat(EditTaskname).isEqualTo("Task1Edit");
		String EditTaskPriority = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table/thead/tr[2]/td[2]")).getText();
		assertThat(EditTaskPriority).isEqualTo("Medium");
		
		driver.findElement(By.id("DeleteTaskButtonTaskList3EditTask1Edit")).click();
//		wait.until(ExpectedConditions.stalenessOf(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table/thead/tr[2]")));
////		wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table/thead/tr[2]"))));
////		driver.
//		driver.manage().timeouts().implicitlyWait(250, TimeUnit.MILLISECONDS);
		Thread.sleep(250);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("DeleteTLButtonTaskList3Edit"))));
		driver.findElement(By.id("DeleteTLButtonTaskList3Edit")).click();
//		driver.manage().timeouts().implicitlyWait(250, TimeUnit.MILLISECONDS);
		Thread.sleep(250);

//		wait.until(ExpectedConditions.stalenessOf(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table/thead/tr[2]")));
//		wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table"))));


		
	} 
	@Test
	public void PriorityOrderTest() throws Exception{
		driver.get("http://127.0.0.1:5500/src/main/resources/static/html/TaskList.html?order=priority");
		
		driver.findElement(By.id("addTLButton")).click();
		WebDriverWait wait = new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("exampleModal"))));
		driver.findElement(By.id("TaskListName")).sendKeys("TaskListA");
		driver.findElement(By.id("TaskListPriority")).sendKeys("Medium");
		driver.findElement(By.id("AddTLSubmit")).click();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("exampleModal"))));
		
		driver.findElement(By.id("addTLButton")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("exampleModal"))));
		driver.findElement(By.id("TaskListName")).sendKeys("TaskListB");
		driver.findElement(By.id("TaskListPriority")).sendKeys("Low");
		driver.findElement(By.id("AddTLSubmit")).click();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("exampleModal"))));
		
		driver.findElement(By.id("addTLButton")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("exampleModal"))));
		driver.findElement(By.id("TaskListName")).sendKeys("TaskListC");
		driver.findElement(By.id("TaskListPriority")).sendKeys("High");
		driver.findElement(By.id("AddTLSubmit")).click();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("exampleModal"))));
		
		String TLPriority1 = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[2]/table")).getAttribute("class");
		assertThat(TLPriority1).isEqualTo("table High");
		String TLPriority2 = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[4]/table")).getAttribute("class");
		assertThat(TLPriority2).isEqualTo("table Medium");
		String TLPriority3 = driver.findElement(By.xpath("//*[@id=\"TaskTables\"]/div[6]/table")).getAttribute("class");
		assertThat(TLPriority3).isEqualTo("table Low");
		
		driver.findElement(By.id("DeleteTLButtonTaskListA")).click();
//		driver.manage().timeouts().implicitlyWait(250, TimeUnit.MILLISECONDS);
		Thread.sleep(250);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("DeleteTLButtonTaskListB"))));
		driver.findElement(By.id("DeleteTLButtonTaskListB")).click();
//		driver.manage().timeouts().implicitlyWait(250, TimeUnit.MILLISECONDS);
		Thread.sleep(250);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("DeleteTLButtonTaskListC"))));
		driver.findElement(By.id("DeleteTLButtonTaskListC")).click();
//		driver.manage().timeouts().implicitlyWait(250, TimeUnit.MILLISECONDS);
		Thread.sleep(250);

		
	}
	@Test
	public void PriorityNavTest() throws Exception{
		driver.get("http://127.0.0.1:5500/src/main/resources/static/html/TaskList.html?order=priority");
		driver.findElement(By.id("priorityNAV")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://127.0.0.1:5500/src/main/resources/static/html/TaskList.html?order=priority");
	}
	@Test
	public void DefaultNavTest() throws Exception{
		driver.get("http://127.0.0.1:5500/src/main/resources/static/html/TaskList.html?order=priority");
		driver.findElement(By.id("defaultNAV")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://127.0.0.1:5500/src/main/resources/static/html/TaskList.html");
	}
	@Test
	public void NameNavTest() throws Exception{
		driver.get("http://127.0.0.1:5500/src/main/resources/static/html/TaskList.html?order=priority");
		driver.findElement(By.id("nameNAV")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://127.0.0.1:5500/src/main/resources/static/html/TaskList.html?order=name");
	}
	@AfterClass
	public static void cleanUp() {
		driver.quit();
	}

}
