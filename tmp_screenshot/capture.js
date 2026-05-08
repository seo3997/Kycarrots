const puppeteer = require('puppeteer');
const fs = require('fs');
const path = require('path');

const BASE_DIR = '/Users/soo/Kycarrots/src/main/resources/static/manual/image/web';

async function setupDirs() {
    const dirs = ['ROLE_SELL', 'ROLE_PROJ', 'ROLE_PUB'];
    for (const dir of dirs) {
        fs.mkdirSync(path.join(BASE_DIR, dir), { recursive: true });
    }
}

async function capture(page, name, url, role) {
    console.log(`    Capturing ${name} at ${url}`);
    try {
        await page.goto(url, { waitUntil: 'networkidle2' });
        await new Promise(r => setTimeout(r, 1500));
        await page.screenshot({ path: path.join(BASE_DIR, role, name), fullPage: true });
    } catch (e) {
        console.log(`    Failed to capture ${name}: ${e.message}`);
    }
}

async function captureSell(browser) {
    const page = await browser.newPage();
    await page.setViewport({ width: 1280, height: 800 });
    
    await page.goto('http://asagong.com/admin/login.do');
    await page.type('input[name="id"]', 'sel1@gmail.com');
    await page.type('input[name="pwd"]', '1234');
    await page.click('input[value="로그인"]');
    await page.waitForNavigation({ waitUntil: 'networkidle2' }).catch(() => {});
    
    const baseUrl = 'http://asagong.com';
    const role = 'ROLE_SELL';

    // Dashboard
    await capture(page, 'dashboard.png', `${baseUrl}/mgt/main/dashBoard.do`, role);

    // Branch
    await capture(page, 'branch_list.png', `${baseUrl}/mgt/branch/selectPageListBranch.do`, role);
    await capture(page, 'branch_insert.png', `${baseUrl}/mgt/branch/insertFormBranch.do`, role);
    await capture(page, 'branch_detail.png', `${baseUrl}/mgt/branch/selectBranch.do?branchId=4`, role);
    await capture(page, 'branch_update.png', `${baseUrl}/mgt/branch/updateFormBranch.do?branchId=4`, role);

    // User
    await capture(page, 'user_list.png', `${baseUrl}/admin/user/selectPageListUserMgt.do`, role);
    await capture(page, 'user_insert.png', `${baseUrl}/admin/user/insertFormUserMgt.do`, role);
    await capture(page, 'user_detail.png', `${baseUrl}/admin/user/selectUserMgt.do?userNo=1`, role);
    await capture(page, 'user_update.png', `${baseUrl}/admin/user/updateFormUserMgt.do?userNo=1`, role);

    // Product
    await capture(page, 'product_list.png', `${baseUrl}/mgt/product/selectPageListProduct.do`, role);
    await capture(page, 'product_insert.png', `${baseUrl}/mgt/product/insertFormProduct.do`, role);
    await capture(page, 'product_detail.png', `${baseUrl}/mgt/product/selectProduct.do?productId=110`, role);
    await capture(page, 'product_update.png', `${baseUrl}/mgt/product/updateFormProduct.do?productId=110`, role);

    // Review
    await capture(page, 'review_list.png', `${baseUrl}/mgt/product/review/selectPageListReview.do`, role);

    // QnA
    await capture(page, 'qna_list.png', `${baseUrl}/mgt/product/qna/selectPageListQna.do`, role);

    // Order
    await capture(page, 'order_list.png', `${baseUrl}/mgt/order/selectPageListOrder.do`, role);
    await capture(page, 'order_detail.png', `${baseUrl}/mgt/order/selectOrder.do?orderId=13`, role);

    // Board
    await capture(page, 'mboard_list.png', `${baseUrl}/mgt/mboard/selectPageListBoard.do`, role);

    await page.close();
}

async function captureProj(browser) {
    const page = await browser.newPage();
    await page.setViewport({ width: 1280, height: 800 });
    
    const client = await page.target().createCDPSession();
    await client.send('Network.clearBrowserCookies');

    await page.goto('http://asagong.com/admin/login.do');
    await page.type('input[name="id"]', 'amsa@gmail.com');
    await page.type('input[name="pwd"]', '1234');
    await page.click('input[value="로그인"]');
    await page.waitForNavigation({ waitUntil: 'networkidle2' }).catch(() => {});

    const baseUrl = 'http://asagong.com';
    const role = 'ROLE_PROJ';

    await capture(page, 'dashboard.png', `${baseUrl}/mgt/main/dashBoard.do`, role);
    
    // Product
    await capture(page, 'product_list.png', `${baseUrl}/mgt/product/selectPageListProduct.do`, role);
    await capture(page, 'product_detail.png', `${baseUrl}/mgt/product/selectProduct.do?productId=110`, role);

    // Order
    await capture(page, 'order_list.png', `${baseUrl}/mgt/order/selectPageListOrder.do`, role);
    await capture(page, 'order_detail.png', `${baseUrl}/mgt/order/selectOrder.do?orderId=13`, role);

    // Review/QnA
    await capture(page, 'review_list.png', `${baseUrl}/mgt/product/review/selectPageListReview.do`, role);
    await capture(page, 'qna_list.png', `${baseUrl}/mgt/product/qna/selectPageListQna.do`, role);

    await page.close();
}

async function capturePub(browser) {
    const page = await browser.newPage();
    await page.setViewport({ width: 414, height: 896 });
    
    await page.goto('http://amsa.asagong.com/', { waitUntil: 'networkidle2' });
    
    const idInput = await page.$('input[name="id"]');
    const pwInput = await page.$('input[name="pwd"]');
    if (idInput && pwInput) {
        await idInput.type('amsa@gmail.com');
        await pwInput.type('1234');
        await page.click('input[value="로그인"]');
        await page.waitForNavigation({ waitUntil: 'networkidle2' }).catch(() => {});
    }
    
    const urls = [
        { url: '/shop/list.do', name: 'list.png' },
        { url: '/shop/detail.do?productId=110', name: 'detail.png' }
    ];

    for (const item of urls) {
        await page.goto(`http://amsa.asagong.com${item.url}`, { waitUntil: 'networkidle2' });
        await new Promise(r => setTimeout(r, 1000));
        await page.screenshot({ path: path.join(BASE_DIR, 'ROLE_PUB', item.name), fullPage: true });
    }
    await page.close();
}

async function run() {
    await setupDirs();
    const browser = await puppeteer.launch({ 
        executablePath: '/Applications/Google Chrome.app/Contents/MacOS/Google Chrome', 
        headless: true, 
        args: ['--no-sandbox', '--disable-setuid-sandbox', '--disable-web-security', '--disable-features=SafeBrowsing'] 
    });
    
    try {
        console.log('Capturing SELL...');
        await captureSell(browser);
        console.log('Capturing PROJ...');
        await captureProj(browser);
        console.log('Capturing PUB...');
        await capturePub(browser);
        console.log('All done');
    } catch (e) {
        console.error(e);
    } finally {
        await browser.close();
    }
}

run();
