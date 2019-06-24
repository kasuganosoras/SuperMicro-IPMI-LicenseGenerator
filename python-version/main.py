#!/usr/bin/python
# -*- coding: UTF-8 -*- 
# 超微主板 IPMI 高级授权生成器 Python 实现版本
import hmac
import binascii
import base64
import sys
import re
from hashlib import sha1
	
def hex2bin(data):
    return binascii.a2b_hex(data)

def hash_hmac(key, code, sha1):
	hmac_code = hmac.new(key, code, sha1)
	return hmac_code.hexdigest()

if __name__ == '__main__':
	if len(sys.argv) == 1 or len(sys.argv) > 2:
		print("Supermicro IPMI License Generator")
		print("https://github.com/kasuganosoras/SuperMicro-IPMI-LicenseGenerator")
		print("Argument is missing, please append your BMC-MAC after this command.")
		print("Example: " + sys.argv[0] + " 0c:c4:7a:3e:2f:de")
		exit()
	mac = sys.argv[1].replace(":", "")
	if re.match( r'^(\w+)$', mac):
		key = hash_hmac(hex2bin("8544E3B47ECA58F9583043F8"), hex2bin(mac), sha1)[0:24]
		s = 0
		result = ""
		for i in range(0, len(key)):
			s = s + 1
			result += key[i]
			if s == 4 and i != len(key) - 1:
				result += "-"
				s = 0
		print(result)
		exit()
	print("Invalid Mac Address!")