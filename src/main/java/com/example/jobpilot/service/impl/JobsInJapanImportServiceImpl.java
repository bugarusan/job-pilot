package com.example.jobpilot.service.impl;

import com.example.jobpilot.entity.Job;
import com.example.jobpilot.repository.JobRepository;
import com.example.jobpilot.service.JobsInJapanImportService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobsInJapanImportServiceImpl implements JobsInJapanImportService {

    private final JobRepository jobRepository;

    public JobsInJapanImportServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void importJobs() {

        try {

            Document listDocument = Jsoup.connect("https://jobsinjapan.com/jobs/")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
                    .referrer("https://www.google.com/")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept", "text/html")
                    .timeout(30000)
                    .get();

            Elements jobs = listDocument.select("article.type-noo_job");

            for (Element element : jobs) {

                try {

                    String title = element.select("h3.loop-item-title a").text();

                    String url = element.select("h3.loop-item-title a")
                            .attr("href");

                    if (jobRepository.existsByUrl(url)) {
                        continue;
                    }

                    String company = element.select("h4.company-name").text();

                    String location = element.select("dd.job-location").text();

                    String salary = "";

                    Elements info = element.select("dl.content-meta dd");

                    if (info.size() > 1) {
                        salary = info.get(1).text();
                    }

                    Document detailDocument = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
                            .referrer("https://www.google.com/")
                            .header("Accept-Language", "en-US,en;q=0.9")
                            .header("Accept", "text/html")
                            .timeout(30000)
                            .get();

                    Element descriptionHeader = detailDocument.selectFirst("h2:contains(Job Description)");

                    StringBuilder description = new StringBuilder();

                    if (descriptionHeader != null) {

                        Element current = descriptionHeader.nextElementSibling();

                        while (current != null) {

                            if ("hr".equals(current.tagName())) {
                                break;
                            }

                            description.append(current.text()).append("\n");

                            current = current.nextElementSibling();
                        }
                    }

                    Job job = new Job();

                    job.setTitle(title);
                    job.setCompany(company);
                    job.setLocation(location);
                    job.setSalary(salary);
                    job.setDescription(description.toString().trim());
                    job.setUrl(url);
                    job.setRemote(false);
                    job.setRequiredSkills(List.of());

                    jobRepository.save(job);

                } catch (Exception e) {

                    System.out.println("Failed to import: "
                            + element.select("h3.loop-item-title a").text());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}