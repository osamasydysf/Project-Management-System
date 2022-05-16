package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Views.InternalFrames.Admin_ManageUsers;
import Views.InternalFrames.Admin_Projects;
import Views.Frames.AdminView;
import Models.AdminModel;
import Models.PersonModel;
import Models.Project;
import Models.ProjectManagerModel;
import Models.Task;
import Models.TeamLeaderModel;
import Views.Panels.Add_User;
import Views.Panels.Update_Users;
import Views.Panels.View_Users;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AdminController {

    private AdminView view;
    private AdminModel model;
    private Admin_ManageUsers admin_ManageUsers;
    private Admin_Projects admin_Projects;
    private Add_User add_User;
    private View_Users view_Users;
    private Update_Users update_Users;
    private JTable UsersTable;

    AdminController(AdminView view, AdminModel model) {
        this.view = view;
        this.model = model;

        this.view.addViewProjectsActionListener(new ViewProjectsActionListener());
        this.view.addManageUsersActionListener(new ManageUsersActionListener());
    }

    private void initializeViewProjects() {
        admin_Projects = new Admin_Projects();

        fillViewProjectsList();

        admin_Projects.AddSelectProjectListener(new ProjectListMouseListener());
    }

    private void initializeManageUsers() {
        admin_ManageUsers = new Admin_ManageUsers();

        add_User = new Add_User();
        view_Users = new View_Users();
        update_Users = new Update_Users();

        fillAddUserManagerNameCombobox();
        fillViewUsersTable();
        addListenersToManageUsersPanels();
        fillManageUsersDynamicPanel();

        addListenersToManageUsersFrame();
    }

    private void addListenersToManageUsersPanels() {
        add_User.AddSelectManagerNameListener(new AddSelectManagerNameActionListener());
        add_User.AddEmployeeButtonListener(new AddUserButtonActionListener());
        add_User.AddSelectRoleListener(new AddSelectRoleActionListener());
        update_Users.AddSearchButtonListener(new SearchActionListener());
        update_Users.DeleteButtonListener(new DeleteActionListener());
        update_Users.UpdateButtonListener(new UpdateActionListener());
    }

    private void addListenersToManageUsersFrame() {
        admin_ManageUsers.AddAddUserListener(new AddUserActionListener());
        admin_ManageUsers.AddUpdateUserListener(new UpdateUserActionListener());
        admin_ManageUsers.AddViewUsersListener(new ViewUserActionListener());
    }

    private void fillViewUsersTable() {
        UsersTable = view_Users.getUsersTable();

        DefaultTableModel tableModel = (DefaultTableModel) UsersTable.getModel();
        List<PersonModel> users = AdminModel.getUsers();
        tableModel.setRowCount(0);
        Object[] rowData = new Object[7];
        users.stream().map(user -> {
            rowData[0] = user.getID();
            return user;
        }).map(user -> {
            rowData[1] = user.getName();
            return user;
        }).map(user -> {
            rowData[2] = user.getAge();
            return user;
        }).map(user -> {
            rowData[3] = user.getUsername();
            return user;
        }).map(user -> {
            rowData[4] = user.getPassword();
            return user;
        }).map(user -> {
            rowData[5] = user.getSalary();
            return user;
        }).map(user -> {
            rowData[6] = user.getRole();
            return user;
        }).forEachOrdered(_item -> {
            tableModel.addRow(rowData);
        });

        view_Users.setUsersTable(UsersTable);
    }

    @SuppressWarnings("unchecked")
    private void fillViewProjectsList() {
        JList<String> ProjectList = admin_Projects.getProjectList();

        List<Project> projects = AdminModel.getProjects();

        DefaultListModel listModel = new DefaultListModel();

        for (Project project : projects) {
            listModel.addElement(project.getName());
            ProjectList.setModel(listModel);
        }

        admin_Projects.setProjectList(ProjectList);
    }

    private void fillAddUserManagerNameCombobox() {
        JComboBox<String> ManagerName = add_User.getManagerName();

        List<PersonModel> manager = AdminModel.getUserByRole("Team Leader");
        System.out.println(manager.get(0).getManagerid());
        add_User.setProjectName(ProjectManagerModel.getProjectByID(manager.get(0).getManagerid()).getName());
        for (int i = 0; i < manager.size(); i++) {
            ManagerName.addItem(manager.get(i).getName());
        }

        add_User.setManagerName(ManagerName);
    }

    private void fillManageUsersDynamicPanel() {
        GridBagLayout layout = new GridBagLayout();

        JPanel DynamicPanel = admin_ManageUsers.getDynamicPanel();

        DynamicPanel.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        DynamicPanel.add(add_User, constraints);
        DynamicPanel.add(view_Users, constraints);
        DynamicPanel.add(update_Users, constraints);

        showAddUserPanel();
        setManageUsersPanels();

        admin_ManageUsers.setDynamicPanel(DynamicPanel);
    }

    private void initializeViewUsersDynamicPanel() {
        JPanel DynamicPanel = admin_ManageUsers.getDynamicPanel();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        DynamicPanel.add(view_Users, constraints);
        admin_ManageUsers.setDynamicPanel(DynamicPanel);
    }

    private void setManageUsersPanels() {
        admin_ManageUsers.setP1(add_User);
        admin_ManageUsers.setP2(view_Users);
        admin_ManageUsers.setP3(update_Users);
    }

    private void showAddUserPanel() {
        add_User.setVisible(true);
        view_Users.setVisible(false);
        update_Users.setVisible(false);
    }

    private void showViewUserPanel() {
        add_User.setVisible(false);
        view_Users.setVisible(true);
        update_Users.setVisible(false);
    }

    private void showUpdateUserPanel() {
        add_User.setVisible(false);
        view_Users.setVisible(false);
        update_Users.setVisible(true);
    }

    //MainFrames Listeners
    class ViewProjectsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            initializeViewProjects();

            view.setAdminProjectsView(admin_Projects);
            view.AddToDesktop(view.getAdminProjectsView());
            view.getAdminProjectsView().setVisible(true);
        }
    }

    class ManageUsersActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            initializeManageUsers();

            view.setAdminManageUsersView(admin_ManageUsers);
            view.AddToDesktop(view.getAdminManageUsersView());
            view.getAdminManageUsersView().setVisible(true);
        }
    }

    //InternalFrames && Panels Listeners
    class AddUserActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            showAddUserPanel();
            setManageUsersPanels();
        }
    }

    class ViewUserActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            fillViewUsersTable();
            initializeViewUsersDynamicPanel();
            showViewUserPanel();

            setManageUsersPanels();
        }
    }

    class UpdateUserActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            showUpdateUserPanel();
            setManageUsersPanels();
        }
    }

    class ProjectListMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent evt) {
            JList<String> ProjectList = admin_Projects.getProjectList();
            JTable TaskTable = admin_Projects.getTaskTable();

            String tmp = (String) ProjectList.getSelectedValue();
            Project project = AdminModel.getProjectByName(tmp);
            List<Task> tasks = AdminModel.getTaskToOneProject(project.getName());

            admin_Projects.setPMName(AdminModel.findUserById(project.getPmId()).getName());
            admin_Projects.setPMID("" + project.getPmId());
            admin_Projects.setProjectName(project.getName());

            admin_Projects.setCompletionBar((int) AdminModel.getCompelationRateByProjectName(project.getName()));

            admin_Projects.setjCompletionPercent(new DecimalFormat("#.##").format(AdminModel.getCompelationRateByProjectName(project.getName())) + "%");

            DefaultTableModel tableModel = (DefaultTableModel) TaskTable.getModel();
            tableModel.setRowCount(0);

            tasks.stream().map(task -> {
                Object rowData[] = new Object[7];
                rowData[0] = task.getTask_id();
                rowData[1] = task.getTask_name();
                rowData[2] = AdminModel.findUserById(task.getEmployee_id()).getName();
                rowData[3] = task.getEmployee_id();
                rowData[4] = task.isTask_completed();
                rowData[5] = AdminModel.findUserById(AdminModel.findUserById(task.getEmployee_id()).getManagerid()).getName();
                rowData[6] = AdminModel.findUserById(AdminModel.findUserById(task.getEmployee_id()).getManagerid()).getID();
                return rowData;
            }).forEachOrdered(rowDate1 -> {
                tableModel.addRow(rowDate1);
            });

            admin_Projects.setProjectList(ProjectList);
            admin_Projects.setTaskTable(TaskTable);

        }
    }

    class AddUserButtonActionListener implements ActionListener {

        JComboBox<String> ManagerName = add_User.getManagerName();
        JComboBox<String> jRoleBox = add_User.getjRoleBox();

        public void actionPerformed(ActionEvent e) {
            if (!add_User.getFirstName().equals("") && !add_User.getLastName().equals("") && !add_User.getjAge().equals("") && !add_User.getjUsername().equals("") && !add_User.getjPassword().equals("") && !add_User.getjSalary().equals("") && !add_User.getProjectName().equals("")) {
                try {

                    PersonModel person = new PersonModel(add_User.getFirstName() + " " + add_User.getLastName(), Integer.parseInt(add_User.getjAge()), add_User.getjUsername(), add_User.getjPassword(), jRoleBox.getSelectedItem().toString(), Double.parseDouble(add_User.getjSalary()));

                    if (jRoleBox.getSelectedItem().equals("Project Manager")) {
                        AdminModel.addProjectManager(person.getName(), person.getAge(), person.getUsername(), "" + add_User.getjPassword(), person.getRole(), person.getSalary());
                        for (PersonModel user : AdminModel.getUsers()) {
                            if (user.getUsername().equals(person.getUsername())) {
                                AdminModel.createProject(user.getID(), add_User.getProjectName());
                            }
                        }
                    } else {
                        PersonModel manager = AdminModel.findUserByName(ManagerName.getSelectedItem().toString());
                        person.setManagerid(manager.getID());
                        AdminModel.addUser(person);
                    }
                    resetPanelData();
                    JOptionPane.showMessageDialog(null, "This Person has been added successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please,fill all the fields", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }

        protected void resetPanelData() {
            add_User.setFirstName("");
            add_User.setLastName("");
            add_User.setjAge("");
            add_User.setjUsername("");
            add_User.setjPassword("");
            add_User.setjSalary("");
            jRoleBox.setSelectedIndex(0);
            ManagerName.setSelectedItem("");
            add_User.setManagerName(ManagerName);
            add_User.setjRoleBox(jRoleBox);
        }
    }

    class AddSelectRoleActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JComboBox<String> ManagerName = add_User.getManagerName();
            JComboBox<String> jRoleBox = add_User.getjRoleBox();

            ManagerName.removeAllItems();
            if (jRoleBox.getSelectedItem().equals("Employee")) {
                List<PersonModel> manager = AdminModel.getUserByRole("Team Leader");
                for (int i = 0; i < manager.size(); i++) {
                    ManagerName.addItem(manager.get(i).getName());
                    ManagerName.setSelectedIndex(0);
                }
            } else if (jRoleBox.getSelectedItem().equals("Team Leader")) {
                List<PersonModel> manager = AdminModel.getUserByRole("Project Manager");
                for (int i = 0; i < manager.size(); i++) {
                    ManagerName.addItem(manager.get(i).getName());
                    ManagerName.setSelectedIndex(0);
                }
            }

            if (jRoleBox.getSelectedItem().equals("Project Manager")) {
                add_User.setProjectNameActive(true);
            } else {
                add_User.setProjectNameActive(false);
            }

            add_User.setManagerName(ManagerName);
            add_User.setjRoleBox(jRoleBox);
        }

    }

    class AddSelectManagerNameActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JComboBox<String> ManagerName = add_User.getManagerName();
            JComboBox<String> jRoleBox = add_User.getjRoleBox();
            if (ManagerName.getSelectedItem() == null) {
                return;
            }
            if (jRoleBox.getSelectedItem().equals("Employee")) {
                List<PersonModel> manager = AdminModel.getUserByRole("Team Leader");
                for (int i = 0; i < manager.size(); i++) {
                    if (ManagerName.getSelectedItem().equals(manager.get(i).getName())) {
                        add_User.setProjectName(ProjectManagerModel.getProjectByID(manager.get(i).getManagerid()).getName());
                    }
                }
            } else if (jRoleBox.getSelectedItem().equals("Team Leader")) {
                List<PersonModel> manager = AdminModel.getUserByRole("Project Manager");
                for (int i = 0; i < manager.size(); i++) {
                    if (ManagerName.getSelectedItem().equals(manager.get(i).getName())) {
                        add_User.setProjectName(ProjectManagerModel.getProjectByID(manager.get(i).getID()).getName());
                    }
                }

            }

            add_User.setManagerName(ManagerName);
            add_User.setjRoleBox(jRoleBox);
        }

    }

    class SearchActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                update_Users.resetPanelData();
                if (!update_Users.getjSearchField().equals("")) {

                    PersonModel returned = AdminModel.findUserById(Integer.parseInt(update_Users.getjSearchField()));
                    if (returned.getID() > 0) {
                        setPanelDate(returned);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "please enter data..", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception a) {
                JOptionPane.showMessageDialog(null, "Wrong ID.." + a.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

            }
        }

        protected void setPanelDate(PersonModel person) {
            update_Users.setjID("" + person.getID());
            update_Users.setFirstName(person.getName().split(" ")[0]);
            update_Users.setLastName(person.getName().split(" ")[1]);
            update_Users.setjAge("" + person.getAge());
            update_Users.setjUsername("" + person.getUsername());
            update_Users.setjPassword("" + person.getPassword());
            update_Users.setjSalary("" + person.getSalary());
            if (person.getRole().equals("Team Leader")) {
                update_Users.setProjectNameActive(false);
                update_Users.setProjectName(ProjectManagerModel.getProjectByID(person.getManagerid()).getName());
            } else if (person.getRole().equals("Project Manager")) {
                update_Users.setProjectNameActive(true);
                update_Users.setProjectName(ProjectManagerModel.getProjectByID(person.getID()).getName());
            } else {
                update_Users.setProjectNameActive(false);
                PersonModel leader = AdminModel.findUserById(person.getManagerid());
                update_Users.setProjectName(ProjectManagerModel.getProjectByID(leader.getManagerid()).getName());
            }
            update_Users.setRole(person.getRole());
        }

    }

    class UpdateActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!update_Users.getjID().equals("") && !update_Users.getFirstName().equals("") && !update_Users.getLastName().equals("") && !update_Users.getjAge().equals("") && !update_Users.getjUsername().equals("") && !update_Users.getjPassword().equals("") && !update_Users.getjSalary().equals("")) {
                try {
                    PersonModel person = new PersonModel(Integer.parseInt(update_Users.getjID()), update_Users.getFirstName() + " " + update_Users.getLastName(), Integer.parseInt(update_Users.getjAge()), update_Users.getjUsername(), update_Users.getjPassword(), update_Users.getRole(), Double.parseDouble(update_Users.getjSalary()));

                    if (update_Users.getRole().equals("Project Manager")) {
                        AdminModel.updateProject(person.getID(), update_Users.getProjectName());
                    }
                    AdminModel.UpdateUser(person);
                    update_Users.resetPanelData();
                    JOptionPane.showMessageDialog(null, "This Employee has been updated successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please,fill all the fields", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class DeleteActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (!update_Users.getjID().equals("") && !update_Users.getFirstName().equals("") && !update_Users.getjAge().equals("") && !update_Users.getjUsername().equals("") && !update_Users.getjPassword().equals("") && !update_Users.getjSalary().equals("")) {
                AdminModel.deleteUser(Integer.parseInt(update_Users.getjSearchField()));
                update_Users.resetPanelData();
                JOptionPane.showMessageDialog(null, "This user has been deleted successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
