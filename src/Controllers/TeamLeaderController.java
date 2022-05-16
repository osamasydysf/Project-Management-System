package Controllers;

import Models.EmployeeModel;
import Models.Penality;
import Models.ProjectManagerModel;
import Models.Report;
import Models.Task;
import java.awt.event.ActionEvent;
import Views.InternalFrames.TL_ViewPenalities;
import Views.InternalFrames.TL_AssignTasks;
import Views.InternalFrames.TL_ManagePenalities;
import Views.InternalFrames.TL_ViewReports;
import Views.InternalFrames.TL_ViewVacations;
import Views.InternalFrames.TL_ViewTasks;
import Views.InternalFrames.TL_ManageVacations;
import Views.Frames.TeamLeaderView;
import Models.TeamLeaderModel;
import Models.Vacation;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TeamLeaderController {

    private TeamLeaderView teamLeaderView;
    private TeamLeaderModel teamLeaderModel;
    private TL_AssignTasks AssignTasksView;
    private TL_ManagePenalities ManagePenalitiesView;
    private TL_ManageVacations ManageVacationsView;
    private TL_ViewPenalities ViewPenalitiesView;
    private TL_ViewReports ViewReportsView;
    private TL_ViewTasks ViewTasksView;
    private TL_ViewVacations ViewVacationsView;

    TeamLeaderController(TeamLeaderView view, TeamLeaderModel model) {
        this.teamLeaderView = view;
        this.teamLeaderModel = model;

        this.teamLeaderView.addAssignTaskActionListener(new AssignTasksActionListener());
        this.teamLeaderView.addManagePenalitiesActionListener(new ManagePenalitiesActionListener());
        this.teamLeaderView.addManageVacationActionListener(new ManageVacationsActionListener());
        this.teamLeaderView.addViewPenalitiesActionListener(new ViewPenalitiesActionListener());
        this.teamLeaderView.addViewReportsActionListener(new ViewReportsActionListener());
        this.teamLeaderView.addViewTasksActionListener(new ViewTasksActionListener());
        this.teamLeaderView.addMViewVacationsActionListener(new ViewVacationsActionListener());
    }

    //MainFrames Listeners
    class AssignTasksActionListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            initializeAssignTasksView();

            teamLeaderView.setTeamLeaderAssignTasks(AssignTasksView);
            teamLeaderView.AddToDesktop(teamLeaderView.getTeamLeaderAssignTasks());
            teamLeaderView.getTeamLeaderAssignTasks().setVisible(true);
        }
    }

    class ManagePenalitiesActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            initializeManagePenalitiesView();

            teamLeaderView.setTeamLeaderManagePenalities(ManagePenalitiesView);
            teamLeaderView.AddToDesktop(teamLeaderView.getTeamLeaderManagePenalities());
            teamLeaderView.getTeamLeaderManagePenalities().setVisible(true);
        }
    }

    class ManageVacationsActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            initializeManageVacationsView();
            
            teamLeaderView.setTeamLeaderManageVacations(ManageVacationsView);
            teamLeaderView.AddToDesktop(teamLeaderView.getTeamLeaderManageVacations());
            teamLeaderView.getTeamLeaderManageVacations().setVisible(true);
        }

    }

    class ViewPenalitiesActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            initializeViewPenalitiesView();

            teamLeaderView.setTeamLeaderViewPenalities(ViewPenalitiesView);
            teamLeaderView.AddToDesktop(teamLeaderView.getTeamLeaderViewPenalities());
            teamLeaderView.getTeamLeaderViewPenalities().setVisible(true);
        }
    }

    class ViewReportsActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            initializeViewReportsView();

            teamLeaderView.setTeamLeaderViewReports(ViewReportsView);
            teamLeaderView.AddToDesktop(teamLeaderView.getTeamLeaderViewReports());
            teamLeaderView.getTeamLeaderViewReports().setVisible(true);
        }
    }

    class ViewTasksActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            initializeViewTasksView();

            teamLeaderView.setTeamLeaderViewTasks(ViewTasksView);
            teamLeaderView.AddToDesktop(teamLeaderView.getTeamLeaderViewTasks());
            teamLeaderView.getTeamLeaderViewTasks().setVisible(true);
        }
    }

    class ViewVacationsActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            initializeViewVacationsView();

            teamLeaderView.setTeamLeaderViewVacations(ViewVacationsView);
            teamLeaderView.AddToDesktop(teamLeaderView.getTeamLeaderViewVacations());
            teamLeaderView.getTeamLeaderViewVacations().setVisible(true);
        }
    }

    //InternalFrames Listeners
    class AssignTasksEmployeeTableMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            JTable EmployeeTable = AssignTasksView.getEmployeeTable();

            EmployeeTable.setColumnSelectionInterval(WIDTH, WIDTH);
            int i = EmployeeTable.getSelectedRow();
            TableModel tableModel = EmployeeTable.getModel();

            System.out.println(i);

            AssignTasksView.setEmployeeIDInput(tableModel.getValueAt(i, 1).toString());
        }
    }

    class AssignTaskButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!AssignTasksView.getTaskNameInput().equals("") && !AssignTasksView.getTaskInfoInput().equals("")) {
                teamLeaderModel.assignTask(AssignTasksView.getTaskNameInput(), AssignTasksView.getTaskInfoInput(), Integer.parseInt(AssignTasksView.getEmployeeIDInput()));
                AssignTasksView.resetPanelData();
                AssignTasksView.displaySuccessMessage("This task has been added successfully");
            } else {
                AssignTasksView.displayErrorMessage("Please,fill all the fields");
            }
        }
    }

    class ManagePenalitiesEmployeeTableMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            JTable EmployeeTable = ManagePenalitiesView.getEmployeeTable();

            EmployeeTable.setColumnSelectionInterval(WIDTH, WIDTH);
            int i = EmployeeTable.getSelectedRow();
            TableModel tableModel = EmployeeTable.getModel();

            ManagePenalitiesView.setEmployeeIDInput(tableModel.getValueAt(i, 0).toString());
        }
    }

    class AssignPenalityButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!ManagePenalitiesView.getPenalityInput().equals("") && !ManagePenalitiesView.getPenalityReasonInput().equals("")) {
                teamLeaderModel.assignPenality(ManagePenalitiesView.getPenalityInput(), ManagePenalitiesView.getPenalityReasonInput(), Integer.parseInt(ManagePenalitiesView.getEmployeeIDInput()));
                ManagePenalitiesView.resetPanelData();
                ManagePenalitiesView.displaySuccessMessage("This penality has been added successfully");
            } else {
                ManagePenalitiesView.displayErrorMessage("Please,fill all the fields");
            }
        }
    }

    class VacationRequestsTableMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            JTable RequestsTable = ManageVacationsView.getRequestsTable();

            RequestsTable.setColumnSelectionInterval(WIDTH, WIDTH);
            int i = RequestsTable.getSelectedRow();
            TableModel tableModel = RequestsTable.getModel();

            ManageVacationsView.setEmployeeIDInput(tableModel.getValueAt(i, 0).toString());
            ManageVacationsView.setEmployeeNameInput(tableModel.getValueAt(i, 1).toString());
        }
    }

    class AcceptVacationButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JTable RequestsTable = ManageVacationsView.getRequestsTable();

            int i = RequestsTable.getSelectedRow();
            DefaultTableModel tableModel = (DefaultTableModel) RequestsTable.getModel();

            Object rowData[] = new Object[4];
            rowData[0] = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
            rowData[1] = tableModel.getValueAt(i, 1).toString();
            rowData[2] = tableModel.getValueAt(i, 2).toString();
            rowData[3] = tableModel.getValueAt(i, 3).toString();
            if (RequestsTable.getSelectedRowCount() == 1) {
                tableModel.removeRow(RequestsTable.getSelectedRow());
            }

            teamLeaderModel.acceptVacation(Integer.parseInt(ManageVacationsView.getEmployeeIDInput()));
            ManageVacationsView.resetPanelData();
            ManageVacationsView.displaySuccessMessage("This vacation has been accepted successfully");
        }
    }

    class DenyVacationButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JTable RequestsTable = ManageVacationsView.getRequestsTable();

            int i = RequestsTable.getSelectedRow();
            DefaultTableModel model2 = (DefaultTableModel) RequestsTable.getModel();

            Object rowData[] = new Object[4];
            rowData[0] = Integer.parseInt(model2.getValueAt(i, 0).toString());
            rowData[1] = model2.getValueAt(i, 1).toString();
            rowData[2] = model2.getValueAt(i, 2).toString();
            rowData[3] = model2.getValueAt(i, 3).toString();
            if (RequestsTable.getSelectedRowCount() == 1) {
                model2.removeRow(RequestsTable.getSelectedRow());
            }

            teamLeaderModel.denyVacation(Integer.parseInt(ManageVacationsView.getEmployeeIDInput()));
            ManageVacationsView.resetPanelData();
            ManageVacationsView.displaySuccessMessage("This vacation has been denied successfully");
        }
    }

    class ReportsListMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            JList<String> ReportList = ViewReportsView.getReportList();

            String reportName = (String) ReportList.getSelectedValue();

            ViewReportsView.setReportDetails(teamLeaderModel.getReportByName(reportName).getDetails());
            ViewReportsView.setEmployeeID("" + teamLeaderModel.getReportByName(reportName).getEmployeeID());
            ViewReportsView.setReportID("" + teamLeaderModel.getReportByName(reportName).getId());

            int i = teamLeaderModel.getReportByName(reportName).getEmployeeID();

            ViewReportsView.setEmployeeName(teamLeaderModel.findEmployeeById(i).getName());
            ViewReportsView.setReportName(teamLeaderModel.getReportByName(reportName).getReportName());
        }
    }

    private void initializeAssignTasksView() {
        AssignTasksView = new TL_AssignTasks();
        fillAssignTasksEmployeeTable();

        AssignTasksView.addAssignTaskButtonActionListener(new AssignTaskButtonActionListener());
        AssignTasksView.addEmployeeTableMouseAdapter(new AssignTasksEmployeeTableMouseListener());
    }

    private void initializeManagePenalitiesView() {
        ManagePenalitiesView = new TL_ManagePenalities();
        fillManagePenalitiesEmployeeTable();

        ManagePenalitiesView.addAssignPenalityButtonActionListener(new AssignPenalityButtonActionListener());
        ManagePenalitiesView.addEmployeeTableMouseAdapter(new ManagePenalitiesEmployeeTableMouseListener());
    }

    private void initializeManageVacationsView() {
        ManageVacationsView = new TL_ManageVacations();
        fillManageVacationsRequestsTable();
        
        ManageVacationsView.addRequestsTableMouseListener(new VacationRequestsTableMouseListener());
        ManageVacationsView.addAcceptVacationButtonActionListener(new AcceptVacationButtonActionListener());
        ManageVacationsView.addDenyVacationButtonActionListener(new DenyVacationButtonActionListener());
    }

    private void initializeViewPenalitiesView() {
        ViewPenalitiesView = new TL_ViewPenalities();
        fillViewPenalitiesPenalityTable();
    }

    private void initializeViewReportsView() {
        ViewReportsView = new TL_ViewReports();
        fillViewReportsList();
        ViewReportsView.addReportListMouseAdapter(new ReportsListMouseListener());
    }

    private void initializeViewTasksView() {
        ViewTasksView = new TL_ViewTasks();
        fillViewTasksTable();
    }

    private void initializeViewVacationsView() {
        ViewVacationsView = new TL_ViewVacations();
        fillViewVacationsTable();
    }

    private void fillAssignTasksEmployeeTable() {
        JTable EmployeeTable = AssignTasksView.getEmployeeTable();

        List<EmployeeModel> employees = teamLeaderModel.getEmployees(teamLeaderModel.getID());
        DefaultTableModel tableModel = (DefaultTableModel) EmployeeTable.getModel();

        Object rowData[] = new Object[4];
        employees.stream().map(employee -> {
            rowData[0] = employee.getName();
            return employee;
        }).map(employee -> {
            rowData[1] = employee.getID();
            return employee;
        }).map(employee -> {
            rowData[2] = employee.getSalary();
            return employee;
        }).map(employee -> {
            rowData[3] = employee.getAge();
            return employee;
        }).forEachOrdered(_item -> {
            tableModel.addRow(rowData);
        });

        AssignTasksView.setEmployeeTable(EmployeeTable);
    }

    private void fillManagePenalitiesEmployeeTable() {
        JTable EmployeeTable = ManagePenalitiesView.getEmployeeTable();

        List<EmployeeModel> employees = teamLeaderModel.getEmployees(teamLeaderModel.getID());
        DefaultTableModel tableModel = (DefaultTableModel) EmployeeTable.getModel();

        Object rowDate[] = new Object[5];
        employees.stream().map(employee -> {
            rowDate[0] = employee.getID();
            System.out.println(employee.getID());
            return employee;
        }).map(employee -> {
            rowDate[1] = employee.getName();
            return employee;
        }).map(employee -> {
            rowDate[2] = employee.getCompletedTasksForEmployee(employee.getID()).size();
            return employee;
        }).map(employee -> {
            rowDate[3] = employee.getNotCompletedTasksForEmployee(employee.getID()).size();
            return employee;
        }).map(employee -> {
            rowDate[4] = employee.getPenalities().size();
            return employee;
        }).forEachOrdered(_item -> {
            tableModel.addRow(rowDate);
        });

        ManagePenalitiesView.setEmployeeTable(EmployeeTable);
    }

    private void fillManageVacationsRequestsTable() {
        JTable RequestsTable = ManageVacationsView.getRequestsTable();

        //List<EmployeeModel> employee = teamLeaderModel.getEmployees(teamLeaderModel.getID());
        List<Vacation> vacations = teamLeaderModel.getVacationRequests();

        //model.findEmployeeById(vacations.get(0).getEmployeeID()).getName();
        DefaultTableModel tableModel = (DefaultTableModel) RequestsTable.getModel();

        Object rowData[] = new Object[4];
        vacations.stream().map(vacation -> {
            rowData[0] = vacation.getEmployeeID();
            return vacation;
        }).map(vacation -> {
            rowData[1] = teamLeaderModel.findEmployeeById(vacation.getEmployeeID()).getName();
            return vacation;
        }).map(vacation -> {
            rowData[2] = vacation.getVacationStart();
            return vacation;
        }).map(vacation -> {
            rowData[3] = vacation.getVacationEnd();
            return vacation;
        }).forEachOrdered(_item -> {
            tableModel.addRow(rowData);
        });

        ManageVacationsView.setRequestsTable(RequestsTable);
    }

    private void fillViewPenalitiesPenalityTable() {
        JTable PenalityTable = ViewPenalitiesView.getPenalityTable();

        List<Penality> penalities = teamLeaderModel.getPenalities();

        DefaultTableModel tableModel = (DefaultTableModel) PenalityTable.getModel();

        Object rowData[] = new Object[5];
        penalities.stream().map(penality -> {
            rowData[0] = penality.getPenalityID();
            return penality;
        }).map(penality -> {
            rowData[1] = penality.getReason();
            return penality;
        }).map(penality -> {
            rowData[2] = penality.getPenality();
            return penality;
        }).map(_item -> {
            rowData[3] = teamLeaderModel.findEmployeeById(_item.getEmployeeID()).getName();
            return _item;
        }).map(_item -> {
            rowData[4] = _item.getEmployeeID();
            return _item;
        }).forEachOrdered(_item -> {
            tableModel.addRow(rowData);
        });

        ViewPenalitiesView.setPenalityTable(PenalityTable);
    }

    private void fillViewReportsList() {
        JList<String> ReportList = ViewReportsView.getReportList();

        List<Report> reports = teamLeaderModel.getReportsForItsEmployees();

        DefaultListModel listModel = new DefaultListModel();

        for (Report report : reports) {
            listModel.addElement(report.getReportName());
            ReportList.setModel(listModel);
        }

        ViewReportsView.setReportList(ReportList);
    }

    private void fillViewTasksTable() {
        JTable TaskTable = ViewTasksView.getTaskTable();

        DefaultTableModel tableModel = (DefaultTableModel) TaskTable.getModel();

        List<Task> tasks = teamLeaderModel.getTasksForItsEmployees();

        Object rowData[] = new Object[7];
        tasks.stream().map(task -> {
            rowData[0] = task.getTask_id();
            return task;
        }).map(task -> {
            rowData[1] = task.getTask_name();
            return task;
        }).map(task -> {
            rowData[2] = task.isTask_completed();
            return task;
        }).map(task -> {
            rowData[3] = task.getTask_info();
            return task;
        }).map(task -> {
            rowData[4] = teamLeaderModel.findEmployeeById(task.getEmployee_id()).getName();
            return task;
        }).map(task -> {
            rowData[5] = task.getEmployee_id();
            return task;
        }).forEachOrdered(_item -> {
            tableModel.addRow(rowData);
        });

        ViewTasksView.setTaskTable(TaskTable);
    }

    private void fillViewVacationsTable() {
        JTable VacationTable = ViewVacationsView.getVacationTable();

        DefaultTableModel tableModel = (DefaultTableModel) VacationTable.getModel();
        List<Vacation> vacations = teamLeaderModel.getVacationRequests();

        Object rowData[] = new Object[4];
        vacations.stream().map(vacation -> {
            rowData[0] = vacation.getID();
            return vacation;
        }).map(vacation -> {
            rowData[1] = vacation.getVacationStart();
            return vacation;
        }).map(vacation -> {
            rowData[2] = vacation.getVacationEnd();
            return vacation;
        }).map(vacation -> {
            rowData[3] = vacation.getEmployeeID();
            return vacation;
        }).forEachOrdered(_item -> {
            tableModel.addRow(rowData);
        });

        ViewVacationsView.setVacationTable(VacationTable);
    }
}