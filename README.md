# 2dv515-webscraper

### Webscraper specifically used to get wikipedia pages
This webscraper gets the html of a given wikipedia page and the outgoing links from that page.
The scraper will then continue to pick a random page from the outoing links found from all scraped pages and scrape that page as well.
The process will go on for 200 iterations and generates a dataset from 200 wiki pages.

The dataset will be split up into 2 folders. 1 folder, "Words" contains the raw text of a wiki page in lower cases and the other folder, "Links",
contains all the outgoing links from a wiki page to another. These links will only include links to other wiki pages and not wiki "books" or other similar pages.

Certain html classes will be removed from each wiki page, these are:
- "catlinks"
- "printfooter"
- "mw-jump-link"
- "mw-editsection"
- "toc"

And the div with id "siteSub" will also be removed from the raw text.

### How to run:
The application is a spring-boot application and requires 3 parameters to be passed at execution.
- root.page
  - Root page to start scraping from
  - Should only be the suffix of a wiki url, e.g. from the url "https://en.wikipedia.org/wiki/Ocean" the value "/wiki/Ocean" should be passed
- category
  - The name of the category, i.e. the name of the folder you want for your dataset.
- dataset.dir
  - The directory where you want the dataset to be stored. This directory needs to already exist.

mvn spring-boot:run -Droot.page=/wiki/Ocean -Dcategory=Oceanic -Ddataset.dir=/my/dataset/dir/
