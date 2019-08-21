import os
import re

service = 'MySQL80'
state_number = -1

os.chdir('C:\\Windows\\system32')
os.system('chcp 437')
status = os.popen('sc query %s' % service).read().split('\n')

for line in status:
    if 'STATE' in line:
        state_number = re.search(r'\d+', line).group()

if state_number is '4':
    os.system('net stop %s' % service)
    raise SystemExit
if state_number is '1':
    os.system('net start %s' % service)
    raise SystemExit
if state_number is '-1':
    raise SystemExit('%s status in unknown' % service)
