web: npm install bcrypt && erb conf/nginx.conf.erb > conf/nginx.conf && mkdir -p logs && touch logs/access.log logs/error.log && (tail -qF -n 0 --pid=$$ logs/*.log &) && (node server/server.js &) && exec bin/nginx -p .
