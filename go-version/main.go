package main

import (
	"encoding/hex"
	"crypto/hmac"
	"crypto/sha1"
	"strings"
	"regexp"
	"fmt"
	"os"
)

func hex2bin(s string) (string) {
	decoded, err := hex.DecodeString(s)
	if err != nil {
		return ""
	}
	return fmt.Sprintf("%s", decoded)
}

func main() {
	if len(os.Args) == 2 {
		mac := strings.Replace(os.Args[1], ":", "", -1)
		if m, _ := regexp.MatchString(`^(\w+)$`, mac); !m {
			fmt.Println("Invalid Mac Address!")
		} else {
			h := hmac.New(sha1.New, []byte(hex2bin("8544E3B47ECA58F9583043F8")))
			h.Write([]byte(hex2bin(mac)))
			key := hex.EncodeToString(h.Sum(nil))[0:24]
			result := ""
			s := 0
			keylen := len([]rune(key))
			for i := 0; i < keylen; i++ {
				s++
				result += string(key[i])
				if s == 4 && i != keylen - 1 {
					result += "-"
					s = 0
				}
			}
			fmt.Println(result)
		}
	} else {
		fmt.Println("Supermicro IPMI License Generator")
		fmt.Println("https://github.com/kasuganosoras/SuperMicro-IPMI-LicenseGenerator")
		fmt.Println("Argument is missing, please append your BMC-MAC after this command.")
		fmt.Println("Example: " + os.Args[0] + " 0c:c4:7a:3e:2f:de")
	}
}
