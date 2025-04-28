package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserComponent {
	
	@Autowired
	UserService us;  
	
	 @Autowired
	    private EmailService emailService;
	 
	 //homepage test 

	@GetMapping("/test")
	public String homePage(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("admin", new Admin());
		 
		return "HealthStack";
	}
	
	// signup user
	
	@GetMapping("/instruction")
	public String instruction() {
		return "instruction";
	}
	
	@GetMapping("/register")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	 @PostMapping("/send-otp")
	    public String sendOtp(@RequestParam("email") String email, HttpSession session, Model model) {
	        // Email Format Check Karna
	        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
	            model.addAttribute("error", "Invalid Email Address!");
	            return "register";
	        }
	        
	        User user1 =  us.getUserByEmail(email);

	        if (user1 != null) {
	            model.addAttribute("error", "⚠️ This email is already registered. Please login or use another email.");
	            return "register"; // same Thymeleaf page
	        }

	        // OTP Generate & Store Karna
	        String otp = emailService.generateOtp();
	        session.setAttribute("otp", otp);
	        session.setAttribute("email", email);
	        emailService.sendOtp(email, otp);

	        model.addAttribute("email", email);
	        return "verify";
	    }
	 
	                                                             // OTP Verify Karna
	    @PostMapping("/verify-otp")
	    public String verifyOtp(@RequestParam("otp") String otp, HttpSession session, Model model) {
	        String sessionOtp = (String) session.getAttribute("otp");

	        if (sessionOtp != null && sessionOtp.equals(otp)) {
	        	session.setAttribute("verified", true); // ✅ OTP verify hone pe flag set karo
	            session.removeAttribute("otp"); // OTP ko remove kar diya taaki reuse na ho
	            model.addAttribute("message", "OTP Verified Successfully!");
	            return "success";
	        } else {
	            model.addAttribute("error", "Wrong OTP! Try Again.");
	            return "verify";
	        }
	    }
	    

		@GetMapping("/signup2")
		public String signup2(HttpSession session , Model model) {
		    Boolean verified = (Boolean) session.getAttribute("verified");

		    if (verified == null || !verified) {
		        return "redirect:/register.html"; // ✅ Agar verified nahi hai toh wapas OTP lene bhej do
		    }

		    model.addAttribute("user", new User());
		    return "signup2"; // ✅ Sirf verified users hi yahan aa sakte hain
		}
		
		@PostMapping("/action")
		public String action(@ModelAttribute User user , HttpSession session) {
			 String email = (String) session.getAttribute("email");
		        if (email == null) {
		            return "redirect:/register";
		        }
		        user.setEmail(email);
		        user.setHealthId("HID" + System.currentTimeMillis());
			us.saveUser(user);
			 return "card";
		}
		
		@GetMapping("/card")
		public String card(HttpSession session, Model model) {
		    User user = (User) session.getAttribute("user");
		    if (user == null) {
		        return "redirect:/register"; // Agar session me user nahi hai to register page bhejo
		    }
		    model.addAttribute("user", user);
		    return "card";
		}
	    
	    
	//user signin
		
		 @PostMapping("/login")
		    public String getUser(@ModelAttribute("user") User user,
	                HttpSession session,Model model , RedirectAttributes redirectAttributes) {
		    	
		    	 User user1 = us.loginUser(user.getEmail(), user.getHealthId());
		    	

		        if (user1 != null) {
		            // Sahi login - session me store karo
		            session.setAttribute("loggedUser", user1);
		            return "redirect:/userDashboard";
		        } else {
		            // Galat login - error message bhejo 
		            redirectAttributes.addFlashAttribute("error", "❌ Wrong credentials, please try again.");
		            model.addAttribute("user", user1);
		            return "redirect:/test"; // redirect with flash message
		        
		            
		        }
		    }
		 
		  @GetMapping("/userDashboard")
		    public String userDashboard(@ModelAttribute("user") User user , HttpSession session , Model model ) {
		    	  User user1 = (User) session.getAttribute("loggedUser");
		    	  
		    	  if (user1 == null) {
			            return "redirect:/test";
			        }	  
		    	  model.addAttribute("user", user1);
		    	return "userDashboard";
		    }
		  	
	
	
	@GetMapping("/signin")
	public String signInPage(Model model) {
		model.addAttribute("user", new User());
		return "signin";
	}
	
	
	
	@Autowired
	RemarkRepository remarkRepo;
	
	@Autowired
	DoctorDashboardService doctordashboardService;
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		long totalDoctors = doctorService.getTotalDoctorCount();
		long totalUsers = us.getTotalUserCount();
		long totalRemarks = remarkRepo.count();
		model.addAttribute("totalDoctors", totalDoctors);
		model.addAttribute("totalUsers", totalUsers);
		model.addAttribute("totalRemarks", totalRemarks);
		 model.addAttribute("doctorOfWeek", doctordashboardService.getDoctorOfTheWeek());

	        // Simulated server load (random % for demo)
	        int serverLoad = new Random().nextInt(41) + 60; // 60 to 100%
	        model.addAttribute("serverLoad", serverLoad);
	        
	        Map<String, Long> patientsPerDoctor = doctordashboardService.getPatientsPerDoctor();
	        model.addAttribute("patientsPerDoctorData", patientsPerDoctor);
	        
	        try {
				model.addAttribute("patientsPerHospitalData", new ObjectMapper().writeValueAsString(doctordashboardService.getPatientsPerHospital().entrySet().removeIf(e -> e.getKey() == null)));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        List<Object[]> areaDiseaseList = remarkRepo.getAreaWiseDiseaseMapping();
	        Map<String, Object> alertInfo = remarkService.getHighestDiseaseArea(areaDiseaseList);

	        model.addAttribute("areaDiseaseList", areaDiseaseList);
	        model.addAttribute("alertInfo", alertInfo);
	        




	        return "dashboard";
		
	}
	

	
	@GetMapping("/success")
	@ResponseBody
	public String success() {
		return "success";
	}
	
	@GetMapping("/doctorlogin")
	public String doctorlogin() {
		return "doctorlogin";
	}
	
	
	
	
	 
	
	    
	    @Autowired
	    private DoctorService doctorService;
	    
	    @PostMapping("/doctorloginplus")
	    public String doctorLogin(@RequestParam String email,
	                              @RequestParam String password,
	                              HttpSession session,
	                              Model model) {

	        // Doctor ko find karo email-password ke basis par
	        Doctor doctor = doctorService.loginDoctor(email, password);

	        if (doctor != null) {
	            // Sahi login - session me store karo
	            session.setAttribute("loggedDoctor", doctor);
	            return "redirect:/newDoctorDashboard";
	        } else {
	            // Galat login - error message bhejo
	            model.addAttribute("error", "❌ Wrong credentials, please try again.");
	            return "doctorlogin";
	        }
	    }
	    
	    @GetMapping("/doctordashboard")
	    public String doctorDashboard(HttpSession session, Model model) {
	        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

	        if (doctor == null) {
	            return "redirect:/doctorlogin";
	        }

	        model.addAttribute("doctor", doctor);
	        return "doctordashboard";
	    }
	    
	    @Autowired private DoctorDashboardService service;
	    

	    // ✅ Fetch user and their remarks
	    @GetMapping("/fetch-patient")
	    public String fetchPatient(@RequestParam String healthId, Model model, HttpSession session) {
	        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");
	        if (doctor == null) return "redirect:/doctorlogin";

	        Optional<User> userOpt = service.getUserByHealthId(healthId);
	        if (userOpt.isPresent()) {
	            model.addAttribute("user", userOpt.get());
	            model.addAttribute("remarksList", service.getRemarksForHealthId(healthId));
	        } else {
	            model.addAttribute("error", "No user found with Health ID: " + healthId);
	        }

	        model.addAttribute("doctor", doctor);
	        return "newdoctorDashboard";
	    }

	    // ✅ Submit remark
	    @PostMapping("/submit-remark")
	    public String submitRemark(@RequestParam String healthId,
	                               @RequestParam String remarks,
	                               @RequestParam String deseasename,
	                               HttpSession session,
	                               RedirectAttributes redirectAttributes) {

	        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");
	        if (doctor == null) return "redirect:/doctorlogin";

	        service.saveRemark(healthId, doctor.getDoctor_id(), doctor.getName(), doctor.getAddress(), deseasename , remarks);
	        redirectAttributes.addAttribute("healthId", healthId); // for redirect with params
	        return "redirect:/fetch-patient";
	    }
	    
	 
	    
	  
	    
	    @GetMapping("/doctorInstruction")
	    public String doctorInstruction() {
	    	return "doctorInstruction";
	    }
	    @GetMapping("/doctorRegister")
	    public String doctorRegister(Model model) {
	    	 model.addAttribute("doctor", new Doctor());
	    	return "doctorRegister";
	    }
	    @GetMapping("/adminDashboard")
	    public String adminDashboard(HttpSession session, Model model) {
	    	 Admin admin = (Admin) session.getAttribute("loggedAdmin");

		        if (admin == null) {
		            return "redirect:/test";
		        }

		        model.addAttribute("admin", admin);
	    	return "adminDashboard";
	    }
	    @GetMapping("/newDoctorDashboard")
	    public String newDoctorDashboard(HttpSession session, Model model) {
	    	 Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");
	    	
		        if (doctor == null ) {
		            return "redirect:/doctorlogin";
		        }
		        if (!doctor.isVerification() ) {
		        	model.addAttribute("error", "your verification is under process");
		            return "redirect:/doctorlogin";
		        }

		        model.addAttribute("doctor", doctor);
	    	return "newDoctorDashboard";
	    }
	    
	    @PostMapping("/registerDoctor")
	    public String registerDoctor(@ModelAttribute Doctor doctor, Model model) {
	        // Check if email already registered
	        if (doctorService.checkDoctorExist(doctor.getEmail())) {
	            return "error";
	        }

	        // Generate random 6-digit doctorId
	        Random random = new Random();
	        int doctorId = 100000 + random.nextInt(900000);
	        doctor.setDoctor_id(doctorId);
	        doctor.setPassword("default123");

	        // Default verification false
	        doctor.setVerification(false);

	        // Save doctor
	        doctorService.saveDoctor(doctor);
	        
	        model.addAttribute("doctor", doctor);

	        return "doctorSuccess";
	    }
       
	    @GetMapping("/doctorSuccess")
	    public String doctorSuccess( @ModelAttribute Doctor doctor, Model model) {
	    	return "doctorSuccess";
	    }
	    
	    @GetMapping("/checkStatus")
	    public String showStatusPage() {
	        return "checkStatus";
	    }
	    
	    @PostMapping("/checkStatus")
	    public String checkDoctorStatus(@RequestParam("doctorId") int doctor_id, Model model) {
	        Optional<Doctor> doctorOptional = doctorService.getDoctorById(doctor_id);

	        	if (doctorOptional.isEmpty()) {
	        	    model.addAttribute("status", "not_found");
	        	}
	        	else {
	        	    Doctor doctor = doctorOptional.get();  // yeh safe hai because upar check kiya hai
	        	    if (doctor.isVerification()) {
	        	        model.addAttribute("status", "verified");
	        	        model.addAttribute("email", doctor.getEmail());
	        	        model.addAttribute("password", doctor.getPassword());
	        	    }
	        	    else {
	        	        model.addAttribute("status", "pending");
	        	    }
	        	}

	        return "checkStatus";
	    }
	    
	    @GetMapping("/adminrequests")
	    public String showPendingRequests(Model model ,  HttpSession session) {
	    	
	    	 Admin admin = (Admin) session.getAttribute("loggedAdmin");

		        if (admin == null) {
		            return "redirect:/test";
		        }
		        
		        else {
	    	
	        List<Doctor> pendingDoctors = doctorService.getAllPendingDoctors();
	        model.addAttribute("doctors", pendingDoctors);
	        return "adminRequests";
		        }
	    }

	    @PostMapping("/adminverify")
	    public String verifyDoctor(@RequestParam("id") int id) {
	        doctorService.verifyDoctor(id);
	        return "redirect:/adminrequests";
	    }

	    @PostMapping("/adminreject")
	    public String rejectDoctor(@RequestParam("id") int id) {
	        doctorService.rejectDoctor(id);
	        return "redirect:/adminrequests";
	    }
	    
	    @Autowired
	    private AdminService as;
	    
	    @GetMapping("/saveadmin")
	    @ResponseBody
	    public String saveAdmin() {
	    	as.saveAdmin();
	    	return "success";
	    }
	    @PostMapping("/adminLogin")
	    public String adminLogin(@ModelAttribute("admin") Admin admin,
                HttpSession session,Model model , RedirectAttributes redirectAttributes) {
	    	
	    	 Admin admin1 = as.loginAdmin(admin.getAdmin_Id(),admin.getPassword());

		        if (admin1 != null) {
		            // Sahi login - session me store karo
		            session.setAttribute("loggedAdmin", admin1);
		            return "redirect:/adminDashboard";
		        } else {
		            // Galat login - error message bhejo 
		            redirectAttributes.addFlashAttribute("error", "❌ Wrong credentials, please try again.");
		            return "redirect:/test"; // redirect with flash message
		        
		            
		        }
	    }
	    
	    @GetMapping("/manageDoctors")
	    public String manageDoctors(Model model, HttpSession session) {
	    	
	    	 Admin admin = (Admin) session.getAttribute("loggedAdmin");

		        if (admin == null) {
		            return "redirect:/test";
		        }
		        
		        else {
	    	
	        List<Doctor> doctors = doctorService.getAllDoctors();
	        model.addAttribute("doctors", doctors);
	        return "ManageDoctors";
		        }
	    }
	    
	    @PostMapping("/removeDoctor")
	    public String removeDoctor(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
	        doctorService.removeDoctor(id);
	        redirectAttributes.addFlashAttribute("success", "Doctor Removed 🗑");
	        return "redirect:/manageDoctors";
	    }
	    
	    @Autowired
	    private RemarkServics remarkService;
	    
	    @GetMapping("/manageUsers")
	    public String manageUsers(Model model,HttpSession session) {
	    	
	    	 Admin admin = (Admin) session.getAttribute("loggedAdmin");

		        if (admin == null) {
		            return "redirect:/test";
		        }
		        
		        else {
	    	
	    	
	        List<User> users = us.getAllUsers();
	        // Fetching remarks for each user
	        Map<Long, List<Remark>> userRemarksMap = new HashMap<>();
	        for (User user : users) {
	            List<Remark> remarks = remarkService.getRemarksByUserId(user.getId());
	            userRemarksMap.put((long) user.getId(), remarks);
	        }

	        model.addAttribute("users", users);
	        model.addAttribute("userRemarksMap", userRemarksMap);  // Adding remarks map to the model
	        return "ManageUsers";
	    }
	    }
	    
	    
	    @PostMapping("/removeUser")
	    public String removeUser(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
	        us.removeUser(id);
	        redirectAttributes.addFlashAttribute("success", "User Removed 🗑");
	        return "redirect:/manageUsers";
	    }
	    
	    @Autowired
	    private UserRepository userRepository;

	    @GetMapping("/analytics")
	    public String showAnalytics(
	            @RequestParam(required = false) String state,
	            @RequestParam(required = false) String district,
	            @RequestParam(required = false) String tehsil,
	            @RequestParam(required = false) String village,
	            Model model) {

	        List<User> users;

	        boolean hasState = state != null && !state.isEmpty();
	        boolean hasDistrict = district != null && !district.isEmpty();
	        boolean hasTehsil = tehsil != null && !tehsil.isEmpty();
	        boolean hasVillage = village != null && !village.isEmpty();

	        if (hasState && hasDistrict && hasTehsil && hasVillage) {
	            users = userRepository.findByStateAndDistrictAndTehsilAndVillage(state, district, tehsil, village);
	        } else if (hasState && hasDistrict && hasTehsil) {
	            users = userRepository.findByStateAndDistrictAndTehsil(state, district, tehsil);
	        } else if (hasState && hasDistrict) {
	            users = userRepository.findByStateAndDistrict(state, district);
	        } else if (hasState) {
	            users = userRepository.findByState(state);
	        } else {
	            users = userRepository.findAll();
	        }

	        model.addAttribute("users", users);
	        return "analytics";
	    }
	    
	    @Autowired
	    RemarkRepository remarkRepository;
	    
	    @GetMapping("/remarkanalytics")
	    public String showRemarks(@RequestParam(required = false) String disease, Model model) {
	        List<Remark> remarks;

	        if (disease != null && !disease.isEmpty()) {
	            remarks = remarkRepository.findByDesease(disease);
	        } else {
	            remarks = remarkRepository.findAll();
	        }

	        model.addAttribute("remarks", remarks);
	        return "remarkanalytics"; // ya remark-analytics.html agar alag bana rahe ho
	    }
	    
	    @GetMapping("/deepanalysis")
	    public String getDiseaseFrequency(Model model) {
	        List<Object[]> data = remarkRepository.countRemarksByDisease();
	        
	        List<Map<String, Object>> diseaseData = new ArrayList<>();
	        for (Object[] row : data) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("disease", row[0]);
	            map.put("count", row[1]);
	            diseaseData.add(map);
	        }

	        model.addAttribute("diseaseData", diseaseData);
	        
	        
	        List<Object[]> data2 = remarkRepository.getDoctorWisePatientCount();

	        List<Map<String, Object>> doctorData = new ArrayList<>();
	        for (Object[] row : data2) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("doctorId", row[0]);
	            map.put("doctorName", row[1]);
	            map.put("count", row[2]);
	            doctorData.add(map);
	        }

	        model.addAttribute("doctorData", doctorData);
	        
	        List<Object[]> trendData = remarkRepository.getPatientTrendByDate();

	        List<Map<String, Object>> timeTrend = new ArrayList<>();
	        for (Object[] row : trendData) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("date", row[0]);
	            map.put("count", row[1]);
	            timeTrend.add(map);
	        }

	        model.addAttribute("timeTrend", timeTrend);
	        
	        
	        List<Object[]> mappingData = remarkRepository.getAreaWiseDiseaseMapping();

	        List<Map<String, Object>> areaDiseaseList = new ArrayList<>();
	        for (Object[] row : mappingData) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("area", row[0]);
	            map.put("disease", row[1]);
	            map.put("count", row[2]);
	            areaDiseaseList.add(map);
	        }

	        model.addAttribute("areaDiseaseList", areaDiseaseList);
	        
	        return "deepanalysis";
	    }
	    
	   /* @GetMapping("/getAppointment")
	    public String showDoctorList(Model model) {
	        List<Doctor> doctorList = doctorService.getAllDoctors();
	        model.addAttribute("doctorList", doctorList);
	        return "getAppointment";
	    }*/
	    
	    @GetMapping("/getAppointment")
	    public String showDoctorList(@RequestParam(value = "specialization", required = false) String specialization,
	                                 Model model,HttpSession session) {
	    	
	    	 User user1 = (User) session.getAttribute("loggedUser");
	    	 
	    	 if (user1 == null) {
		            return "redirect:/test";
		        }
	    	
	        List<Doctor> doctorList;

	        if (specialization != null && !specialization.isEmpty()) {
	            doctorList = doctorService.getDoctorsBySpecialization(specialization);
	            model.addAttribute("selectedSpecialization", specialization);
	        } else {
	            doctorList = doctorService.getAllDoctors();
	        }

	        // Send all specializations for dropdown
	        List<String> specializations = Arrays.asList(
	                "General Physician", "Cardiologist", "Dermatologist", "Neurologist",
	                "Orthopedic", "Pediatrician", "Psychiatrist", "Gynecologist", "ENT Specialist",
	                "Dentist", "Ophthalmologist", "Oncologist", "Urologist", "Pulmonologist",
	                "Gastroenterologist", "Nephrologist"
	        );

	        model.addAttribute("specializations", specializations);
	        model.addAttribute("doctorList", doctorList);

	        return "getAppointment";
	    }
	    
	    @Autowired
	    private AppointmentRepository appointmentRepo;
	    
	    @PostMapping("/bookAppointment")
	    public String bookAppointment(
	            @RequestParam int doctorId,
	            @RequestParam String doctorName,
	            @RequestParam String doctorSpecialization,
	            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate,
	            HttpSession session,
	            RedirectAttributes redirectAttributes) {
	    	
	    	 User user1 = (User) session.getAttribute("loggedUser");
	    	 

	        String patientId = user1.getHealthId();

	        Appointment appt = new Appointment();
	        appt.setDoctorId(doctorId);
	        appt.setDoctorName(doctorName);
	        appt.setDoctorSpecialization(doctorSpecialization);
	        appt.setAppointmentDate(appointmentDate);
	        appt.setRequestTime(LocalDateTime.now());
	        appt.setStatus("Pending");
	        appt.setPatientId(patientId);
	        appt.setPatientName("N/A"); // Optional, you can fetch and set actual name

	        appointmentRepo.save(appt);
	        redirectAttributes.addFlashAttribute("msg", "✅ Appointment request sent successfully!");
	        return "redirect:/getAppointment";
	    }
	    
	    @GetMapping("/checkAppointment")
	    public String viewUserAppointments(HttpSession session, Model model) {
	        User user = (User) session.getAttribute("loggedUser");
	        
	        if (user == null) {
	            return "redirect:/test"; // login page or access denied
	        }

	        List<Appointment> appointments = appointmentRepo.findByPatientId(user.getHealthId());
	        model.addAttribute("appointments", appointments);

	        return "checkAppointment"; // this is the Thymeleaf template we'll create
	    }
	    
	    @GetMapping("/checkAppointmentDoctor")
	    public String viewUserAppointmentsDoctor(HttpSession session, Model model) {
	    	 Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");
		    	
		        if (doctor == null ) {
		            return "redirect:/doctorlogin";
		        }

	        List<Appointment> appointments = appointmentRepo.findByDoctorId(doctor.getDoctor_id());
	        model.addAttribute("appointments", appointments);

	        return "checkAppointmentDoctor"; // this is the Thymeleaf template we'll create
	    }
	    
	    @PostMapping("/acceptAppointment")
	    public String acceptAppointment(@RequestParam int appointmentId) {
	        Appointment app = appointmentRepo.findById(appointmentId).orElse(null);
	        if (app != null) {
	            app.setStatus("accepted");
	            appointmentRepo.save(app);
	        }
	        return "redirect:/checkAppointmentDoctor";
	    }

	    @PostMapping("/rejectAppointment")
	    public String rejectAppointment(@RequestParam int appointmentId) {
	        Appointment app = appointmentRepo.findById(appointmentId).orElse(null);
	        if (app != null) {
	            app.setStatus("rejected");
	            appointmentRepo.save(app);
	        }
	        return "redirect:/checkAppointmentDoctor";
	    }

	    @PostMapping("/markVisited")
	    public String markVisited(@RequestParam int appointmentId) {
	        Appointment app = appointmentRepo.findById(appointmentId).orElse(null);
	        if (app != null) {
	            app.setStatus("visited");
	            
	            appointmentRepo.save(app);
	        }
	        return "redirect:/checkAppointmentDoctor";
	    }
	    
	    @GetMapping("/healthstack")
	    public String main() {
	    	return "main";
	    	}
	    
	    @GetMapping("/workflow")
	    public String workflow() {
	    	return "workflow";
	    	}

	}

	   
	    
	
	    
	    
	

	    
	    
	   
