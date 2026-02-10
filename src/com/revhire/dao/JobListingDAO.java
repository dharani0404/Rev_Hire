package com.revhire.dao;

import com.revhire.model.JobListing;
import com.revhire.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobListingDAO {

    public boolean createJob(JobListing job) {
        String sql = " INSERT INTO job_listings (employer_id, title, description, skills_required, experience_required, education, location, \r\n"+ "salary_min, salary_max, job_type, status, deadline)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'OPEN', ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, job.getEmployerId());
            ps.setString(2, job.getTitle());
            ps.setString(3, job.getDescription());
            ps.setString(4, job.getSkillsRequired());
            ps.setDouble(5, job.getExperienceRequired());
            ps.setString(6, job.getEducation());
            ps.setString(7, job.getLocation());
            ps.setDouble(8, job.getSalaryMin());
            ps.setDouble(9, job.getSalaryMax());
            ps.setString(10, job.getJobType());
            ps.setDate(11, job.getDeadline());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<JobListing> getJobsByEmployer(int employerId) {
        List<JobListing> list = new ArrayList<>();

        String sql = "SELECT * FROM job_listings WHERE employer_id = ? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JobListing job = mapRow(rs);
                list.add(job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

//    public JobListing getJobById(int jobId) {
//        String sql = "SELECT * FROM job_listings WHERE job_id = ?";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, jobId);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                return mapRow(rs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public JobListing getJobById(int jobId, int employerId) {
//        String sql = "SELECT * FROM job_listings WHERE job_id = ? AND employer_id = ?";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, jobId);
//            ps.setInt(2, employerId);
//
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                return mapRow(rs);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    	
 // 1️ For general view (View Job Details)
    public JobListing getJobById(int jobId) {
        String sql = "SELECT * FROM job_listings WHERE job_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } 
    

    // 2️ For secure edit (only employer’s own job)
    public JobListing getJobById(int jobId, int employerId) {
        String sql = "SELECT * FROM job_listings WHERE job_id = ? AND employer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setInt(2, employerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private JobListing mapRow(ResultSet rs) throws SQLException {
        JobListing job = new JobListing();
        job.setJobId(rs.getInt("job_id"));
        job.setEmployerId(rs.getInt("employer_id"));
        job.setTitle(rs.getString("title"));
        job.setDescription(rs.getString("description"));
        job.setSkillsRequired(rs.getString("skills_required"));
        job.setExperienceRequired(rs.getDouble("experience_required"));
        job.setEducation(rs.getString("education"));
        job.setLocation(rs.getString("location"));
        job.setSalaryMin(rs.getDouble("salary_min"));
        job.setSalaryMax(rs.getDouble("salary_max"));
        job.setJobType(rs.getString("job_type"));
        job.setStatus(rs.getString("status"));
        job.setDeadline(rs.getDate("deadline"));
        job.setCreatedAt(rs.getTimestamp("created_at"));
        return job;
    }
    public boolean updateJob(JobListing job) {
        String sql = "UPDATE job_listings SET title=?, description=?, skills_required=?, experience_required=?, " +
                "education=?, location=?, salary_min=?, salary_max=?, job_type=?, deadline=? " +
                "WHERE job_id=? AND employer_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, job.getTitle());
            ps.setString(2, job.getDescription());
            ps.setString(3, job.getSkillsRequired());
            ps.setDouble(4, job.getExperienceRequired());
            ps.setString(5, job.getEducation());
            ps.setString(6, job.getLocation());
            ps.setDouble(7, job.getSalaryMin());
            ps.setDouble(8, job.getSalaryMax());
            ps.setString(9, job.getJobType());
            ps.setDate(10, job.getDeadline());
            ps.setInt(11, job.getJobId());
            ps.setInt(12, job.getEmployerId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
//    public JobListing getJobByIdAndEmployer(int jobId, int employerId) {
//        String sql = "SELECT * FROM job_listings WHERE job_id = ? AND employer_id = ?";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, jobId);
//            ps.setInt(2, employerId);
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return mapRow(rs);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public JobListing getJobByIdAndEmployer(int jobId, int employerId) {
        JobListing job = null;
        String sql = "SELECT * FROM job_listings WHERE job_id = ? AND employer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setInt(2, employerId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                job = new JobListing();
                job.setJobId(rs.getInt("job_id"));
                job.setEmployerId(rs.getInt("employer_id"));
                job.setTitle(rs.getString("title"));
                job.setDescription(rs.getString("description"));
                job.setLocation(rs.getString("location"));
                job.setSalaryMin(rs.getDouble("salary_min"));
                job.setSalaryMax(rs.getDouble("salary_max"));
                
                job.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return job;
    }


    public boolean toggleJobStatus(int jobId, int employerId, String newStatus) {
        String sql = "UPDATE job_listings SET status = ? WHERE job_id = ? AND employer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, jobId);
            ps.setInt(3, employerId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteJob(int jobId, int employerId) {
        String sql = "DELETE FROM job_listings WHERE job_id = ? AND employer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setInt(2, employerId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int countJobsByEmployer(int employerId) {
        String sql = "SELECT COUNT(*) FROM job_listings WHERE employer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countJobsByStatus(int employerId, String status) {
        String sql = "SELECT COUNT(*) FROM job_listings WHERE employer_id = ? AND status = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countTotalApplicationsForEmployer(int employerId) {
        String sql = 
        		"SELECT COUNT(a.application_id) " +
        	            "FROM applications a " +
        	            "JOIN job_listings j ON a.job_id = j.job_id " +
        	            "WHERE j.employer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Object[]> getApplicationsCountPerJob(int employerId) {
        List<Object[]> list = new ArrayList<>();

        String sql = 
        		"SELECT j.job_id, j.title, COUNT(a.application_id) AS app_count " +
        	            "FROM job_listings j " +
        	            "LEFT JOIN applications a ON j.job_id = a.job_id " +
        	            "WHERE j.employer_id = ? " +
        	            "GROUP BY j.job_id, j.title " +
        	            "ORDER BY j.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getInt("job_id");
                row[1] = rs.getString("title");
                row[2] = rs.getInt("app_count");
                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

 // this is for jobseeker
 	public List<JobListing> getAllActiveJobs() {
 	    List<JobListing> list = new ArrayList<>();
 	    String sql = "SELECT j.*, e.company_name FROM job_listings j " +
 	                 "JOIN employer_profiles e ON j.employer_id = e.employer_id " +
 	                 "WHERE j.status = 'OPEN'";
 
 	    try (Connection con = DBConnection.getConnection();
 	         PreparedStatement ps = con.prepareStatement(sql);
 	         ResultSet rs = ps.executeQuery()) {
 
 	        while (rs.next()) {
 	            JobListing job = new JobListing();
 	            job.setJobId(rs.getInt("job_id"));
 	            job.setTitle(rs.getString("title"));
 	            job.setLocation(rs.getString("location"));
 	            job.setSalaryMin(rs.getDouble("salary_min"));
 	            job.setSalaryMax(rs.getDouble("salary_max"));
 	            job.setCompanyName(rs.getString("company_name"));
 	            list.add(job);
 	        }
 	    } catch (Exception e) {
 	        e.printStackTrace();
 	    }
 	    return list;
 	}
 	
 	public JobListing getJobDetailsById(int jobId) {
 	    JobListing job = null;

 	    String sql = "SELECT j.*, e.company_name " +
 	                 "FROM job_listings j " +
 	                 "JOIN employer_profiles e ON j.employer_id = e.employer_id " +
 	                 "WHERE j.job_id = ?";

 	    try (Connection con = DBConnection.getConnection();
 	         PreparedStatement ps = con.prepareStatement(sql)) {

 	        ps.setInt(1, jobId);

 	        ResultSet rs = ps.executeQuery();

 	        if (rs.next()) {
 	            job = new JobListing();
 	            job.setJobId(rs.getInt("job_id"));
 	            job.setTitle(rs.getString("title"));
 	            job.setDescription(rs.getString("description"));
 	            job.setLocation(rs.getString("location"));
 	            job.setExperienceRequired(rs.getDouble("experience_required"));
 	            job.setSalaryMin(rs.getDouble("salary_min"));
 	            job.setSalaryMax(rs.getDouble("salary_max"));
 	            job.setJobType(rs.getString("job_type"));
 	            job.setCompanyName(rs.getString("company_name"));
 	        }

 	    } catch (Exception e) {
 	        e.printStackTrace();
 	    }

 	    return job;
 	}

 	public JobListing getJobDetailsForJobSeeker(int jobId) {

 	    String sql = "SELECT j.*, e.company_name " +
 	                 "FROM job_listings j " +
 	                 "JOIN employer_profiles e ON j.employer_id = e.employer_id " +
 	                 "WHERE j.job_id = ?";

 	    try (Connection con = DBConnection.getConnection();
 	         PreparedStatement ps = con.prepareStatement(sql)) {

 	        ps.setInt(1, jobId);
 	        ResultSet rs = ps.executeQuery();

 	        if (rs.next()) {
 	            JobListing job = mapRow(rs);

 	            // ✅ explicitly set company name
 	            job.setCompanyName(rs.getString("company_name"));

 	            return job;
 	        }

 	    } catch (Exception e) {
 	        e.printStackTrace();
 	    }
 	    return null;
 	}
 	
 	public List<JobListing> searchJobs(String role, String location, Double minExp,
            Double minSalary, Double maxSalary,
            String companyName, String jobType) {

				List<JobListing> list = new ArrayList<>();
				
				StringBuilder sql = new StringBuilder(
				"SELECT j.*, e.company_name " +
				"FROM job_listings j " +
				"JOIN employer_profiles e ON j.employer_id = e.employer_id " +
				"WHERE j.status = 'OPEN' ");
				
				if (role != null && !role.isBlank()) {
				sql.append(" AND j.title LIKE ? ");
				}
				if (location != null && !location.isBlank()) {
				sql.append(" AND j.location LIKE ? ");
				}
				if (minExp != null) {
				sql.append(" AND j.experience_required >= ? ");
				}
				if (minSalary != null) {
				sql.append(" AND j.salary_min >= ? ");
				}
				if (maxSalary != null) {
				sql.append(" AND j.salary_max <= ? ");
				}
				if (companyName != null && !companyName.isBlank()) {
				sql.append(" AND e.company_name LIKE ? ");
				}
				if (jobType != null && !jobType.isBlank()) {
				sql.append(" AND j.job_type = ? ");
				}
				
				try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql.toString())) {
				
						int index = 1;
						
						if (role != null && !role.isBlank()) {
						ps.setString(index++, "%" + role + "%");
						}
						if (location != null && !location.isBlank()) {
						ps.setString(index++, "%" + location + "%");
						}
						if (minExp != null) {
						ps.setDouble(index++, minExp);
						}
						if (minSalary != null) {
						ps.setDouble(index++, minSalary);
						}
						if (maxSalary != null) {
						ps.setDouble(index++, maxSalary);
						}
						if (companyName != null && !companyName.isBlank()) {
						ps.setString(index++, "%" + companyName + "%");
						}
						if (jobType != null && !jobType.isBlank()) {
						ps.setString(index++, jobType.toUpperCase());
						}
						
						ResultSet rs = ps.executeQuery();
				
						while (rs.next()) {
						JobListing job = new JobListing();
						job.setJobId(rs.getInt("job_id"));
						job.setTitle(rs.getString("title"));
						job.setLocation(rs.getString("location"));
						job.setSalaryMin(rs.getDouble("salary_min"));
						job.setSalaryMax(rs.getDouble("salary_max"));
						job.setExperienceRequired(rs.getDouble("experience_required"));
						job.setJobType(rs.getString("job_type"));
						job.setCompanyName(rs.getString("company_name"));
						list.add(job);
						}
				
				} catch (Exception e) {
				e.printStackTrace();
				}
				
			return list;
		}

 			

}

