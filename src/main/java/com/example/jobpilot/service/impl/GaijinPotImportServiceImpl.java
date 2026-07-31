package com.example.jobpilot.service.impl;

import com.example.jobpilot.entity.Job;
import com.example.jobpilot.repository.JobRepository;
import com.example.jobpilot.service.GaijinPotImportService;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class GaijinPotImportServiceImpl implements GaijinPotImportService {

    private static final String BASE_URL = "https://jobs.gaijinpot.com";
    private static final String JOBS_URL = BASE_URL + "/en/job";

    private final JobRepository jobRepository;

    public GaijinPotImportServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void importJobs() {

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(true)
            );

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
            );

            Page page = context.newPage();

            page.navigate(
                    JOBS_URL,
                    new Page.NavigateOptions()
                            .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
            );

            Document document = Jsoup.parse(page.content());

            Elements jobs = document.select("div.gpjs-open-link");

            System.out.println("Jobs found : " + jobs.size());

            for (Element element : jobs) {

                String url = "https://jobs.gaijinpot.com"
                        + element.attr("data-href");

                if (jobRepository.existsByUrl(url)) {
                    continue;
                }

                String title = element.select("h3.card-heading a").text();

                Elements details = element.select("dd");

                String company = details.size() > 1
                        ? details.get(1).text()
                        : "";

                String salary = details.size() > 2
                        ? details.get(2).text()
                        : "";

                String location = details.size() > 3
                        ? details.get(3).text()
                        : "";

                page.navigate(
                        url,
                        new Page.NavigateOptions()
                                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                                .setTimeout(10000)
                );

                String detailHtml = page.content();

                Document detailDocument = Jsoup.parse(detailHtml);

                String description = "";

                Element descriptionTitle = detailDocument.selectFirst(
                        "h3.card-heading:contains(Description)"
                );

                if (descriptionTitle != null) {

                    Element descriptionElement =
                            descriptionTitle.parent().nextElementSibling();

                    if (descriptionElement != null) {
                        description = descriptionElement.text();
                    }
                }

                Job job = new Job();

                job.setTitle(title);
                job.setCompany(company);
                job.setSalary(salary);
                job.setLocation(location);
                job.setDescription(description);
                job.setRemote(false);
                job.setUrl(url);

                jobRepository.save(job);

            }

            context.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}