echo "Switching to branch master"
git checkout master

echo "Building app"
npm run build

echo "Deploying files to server"
scp -r build/* root@147.232.205.203:/var/www/147.232.205.203/

echo "Done!"