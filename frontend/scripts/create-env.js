const fs = require('fs');
const path = require('path');

const apiUrl = process.env.BARBEARIA_API_URL ?? '';
const outputPath = path.join(__dirname, '..', 'public', 'env.js');
const content = `window.BARBEARIA_API_URL = '${apiUrl.replace(/'/g, "\\'")}';\n`;

fs.writeFileSync(outputPath, content, 'utf8');
console.log(`BARBEARIA_API_URL configured as: ${apiUrl || '(local proxy)'}`);